package com.lumen1024.groupeventer.entities.user_data.model

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FieldValue
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupColor
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.entities.group_event.model.toGroupEventDto
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class UserDataService @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userDataRepository: UserDataRepository,
) {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    private val _groups = mutableStateListOf<Group>()
    val groups get() = MutableStateFlow(_groups.toImmutableList()).asStateFlow()

    suspend fun joinGroup(name: String, password: String): Result<Unit> {
        val user = user.value ?: return Result.failure(Throwable("Not authorized"))

        val userData = userData.value
            ?: return Result.failure(Throwable("ded")) // todo

        val group = groupRepository.get(name, password)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id in userData.groups) {
            return Result.failure(Throwable("You already in this group"))
        }

        userDataRepository.updateUserData(
            userId = user.id,
            data = mapOf(
                "groups" to userData.groups + group.id
            )
        ).onFailure { return Result.failure(it) }

        groupRepository.updateGroup(
            groupId = group.id,
            data = mapOf(
                "people" to group.people + user.id
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    suspend fun leaveGroup(name: String): Result<Unit> {
        val user = user.value ?: return Result.failure(Throwable("Not authorized"))

        val userData = userData.value
            ?: return Result.failure(Throwable("ded")) // todo

        val group = groupRepository.get(name, null)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id !in userData.groups) {
            return Result.failure(Throwable("You are not in this group"))
        }

        userDataRepository.updateUserData(
            userId = user.id,
            data = mapOf(
                "groups" to userData.groups - group.id
            )
        ).onFailure { return Result.failure(it) }

        val isLast = group.people.isEmpty()
        val isAdmin = group.admin == user.id

        if (isLast) {
            groupRepository.removeGroup(
                groupId = group.id
            ).onFailure { return Result.failure(it) }
        } else if (isAdmin) {
            // new admin
            val newAdmin = group.people[0]
            groupRepository.updateGroup(
                groupId = group.id,
                mapOf(
                    Group::admin.name to newAdmin
                )
            ).onFailure { return Result.failure(it) }

            // remove from common people
            groupRepository.updateGroup(
                groupId = group.id,
                mapOf(
                    Group::people.name to group.people - newAdmin
                )
            ).onFailure { return Result.failure(it) }
        } else {
            groupRepository.updateGroup(
                groupId = group.id,
                data = mapOf(
                    "people" to group.people - user.id
                )
            ).onFailure { return Result.failure(it) }
        }
        return Result.success(Unit)
    }

    suspend fun createGroup(name: String, password: String, color: GroupColor): Result<Unit> {
        if (groupRepository.get(name = name, password = null).isSuccess) {
            return Result.failure(Throwable("Group with same name already exist"))
        } // todo: move to addGroup

        val user = user.value ?: return Result.failure(Throwable("Not authorized"))

        val userData = userData.value
            ?: return Result.failure(Throwable("Not authorized")) // todo

        val group = Group(
            name = name,
            password = password,
            admin = user.id,
            color = color
        )

        groupRepository.add(group)

        userDataRepository.updateUserData(
            userId = user.id,
            data = mapOf(
                "groups" to userData.groups + group.id // todo
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    suspend fun updateGroup(map: Map<String, Any>): Result<Unit> {
        TODO()
    }

    suspend fun createEvent(event: GroupEvent, group: Group): Result<Unit> {
        if (!userInGroup(group)) return Result.failure(Throwable("ded")) // todo


        groupRepository.updateGroup(
            groupId = group.id,
            data = mapOf(
                "events" to (group.events.map { it.toGroupEventDto() } + event.toGroupEventDto())
            )
        ).onFailure { return Result.failure(it) }
        return Result.success(Unit)
    }

    suspend fun updateEvent(event: GroupEvent): Result<Unit> {
        val targetGroup = groups.value.let {
            it.forEach { group ->
                if (event in group.events)
                    return@let group
            }
            return@let null
        } ?: return Result.failure(Throwable("ded")) // todo

        val id = user.value?.id ?: return Result.failure(Throwable("ded")) // todo

        if (id != event.creator) return Result.failure(Throwable("ded")) // todo

        groupRepository.updateGroup(
            targetGroup.id, mapOf(
                Group::events.name to FieldValue.arrayUnion(event) // todo maybe not work
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    suspend fun deleteEvent(event: GroupEvent): Result<Unit> {
        val targetGroup = groups.value.let {
            it.forEach { group ->
                if (event in group.events)
                    return@let group
            }
            return@let null
        } ?: return Result.failure(Throwable("ded")) // todo

        val id = user.value?.id ?: return Result.failure(Throwable("ded")) // todo

        if (id != event.creator) return Result.failure(Throwable("ded")) // todo

        groupRepository.updateGroup(
            targetGroup.id, mapOf(
                Group::events.name to FieldValue.arrayRemove(event)
            )
        )
        return Result.success(Unit)
    }

    private var unsubscribeFromUserChanges: (() -> Unit)? = null
    private var unsubscribeFromUserDataChanges: (() -> Unit)? = null
    private var unsubscribeFromGroupChanges: (() -> Unit)? = null

    init {
        unsubscribeFromUserChanges?.let { it() }
        unsubscribeFromUserChanges = authService.listenChanges(this::updateUser)

        unsubscribeFromUserDataChanges?.let {
            it()
            _userData.value = null
        }
        unsubscribeFromUserDataChanges = listenUserDataChanges()
    }

    private fun updateUser(user: User?) {
        _user.value = user
    }

    private suspend fun userInGroup(group: Group): Boolean {
        return userData.value?.groups?.let { return@let (group.id in it) }
            ?: false // todo
    }

    private fun listenUserDataChanges(): (() -> Unit)? = user.value?.let {
        return userDataRepository.listen(it.id) { data ->
            _userData.value = data
            unsubscribeFromGroupChanges?.let {
                it()
                _groups.clear()
            }
            unsubscribeFromGroupChanges = listenGroupChanges()
        }
    }

    /** Listen changes stored in [userData] groups ids.
     *
     * also return callback to remove this listener
     * */
    private fun listenGroupChanges(): (() -> Unit)? =
        userData.value?.groups?.takeIf { it.isNotEmpty() }?.let {
            return groupRepository.listenList(it) { changes ->
                processGroupsChange(changes)
            }
        }

    /** Change inner field [groups] for every [RepositoryObjectChange] in list*/
    private fun processGroupsChange(repositoryObjectChanges: List<RepositoryObjectChange<Group?>>) {
        repositoryObjectChanges
            .filter { it.data != null }
            .forEach { change ->
                when (change.type) {
                    RepositoryObjectChange.Type.ADDED,
                    RepositoryObjectChange.Type.MODIFIED,
                    -> {
                        val index = _groups.indexOfFirst { group -> group.id == change.data!!.id }

                        if (index == -1) {
                            _groups.add(change.data!!)
                        } else {
                            _groups[index] = change.data!!
                        }
                    }

                    RepositoryObjectChange.Type.REMOVED -> _groups.remove(change.data!!)
                }
            }
    }
}