package com.lumen1024.groupeventer.entities.user.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupColor
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class UserService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
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

        val group = groupRepository.getGroup(name, password)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id in userData.groups) {
            return Result.failure(Throwable("You already in this group"))
        }

        userRepository.updateData(
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

        val group = groupRepository.getGroup(name, null)
            .fold(onSuccess = { it }, onFailure = { return Result.failure(it) })

        if (group.id !in userData.groups) {
            return Result.failure(Throwable("You are not in this group"))
        }

        userRepository.updateData(
            userId = user.id,
            data = mapOf(
                "groups" to userData.groups - group.id
            )
        ).onFailure { return Result.failure(it) }

        groupRepository.updateGroup(
            groupId = group.id,
            data = mapOf(
                "people" to group.people - user.id
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    suspend fun createGroup(name: String, password: String, color: GroupColor): Result<Unit> {
        if (groupRepository.getGroup(name = name, password = null).isSuccess) {
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

        groupRepository.addGroup(group)

        userRepository.updateData(
            userId = user.id,
            data = mapOf(
                "groups" to userData.groups + group.id
            )
        ).onFailure { return Result.failure(it) }

        return Result.success(Unit)
    }

    suspend fun addEvent(event: GroupEvent, group: Group) {
        groupRepository.updateGroup(
            groupId = group.id,
            data = mapOf(
                "events" to group.events.toMutableList().add(event)
            )
        )
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

    private fun listenUserDataChanges(): (() -> Unit)? = user.value?.let {
        return userRepository.listenChanges(it.id) { data ->
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
            return groupRepository.listenChanges(it) { changes ->
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