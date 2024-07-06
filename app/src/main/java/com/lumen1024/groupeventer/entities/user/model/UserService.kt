package com.lumen1024.groupeventer.entities.user.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
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
        val group = groupRepository.getGroup(name, password).getOrNull()
            ?: return Result.failure(Throwable("ded")) // todo
        val userData = userData.value
            ?: return Result.failure(Throwable("ded")) // todo

        userRepository.updateData(
            userId = userData.id,
            data = mapOf(
                "groups" to userData.groups.toMutableList().add(group.id)
            )
        ).also {
            if (it.isFailure)
                return Result.failure(Throwable("ded")) // todo
        }

        groupRepository.updateGroup(
            groupId = group.id,
            data = mapOf(
                "people" to group.people.toMutableList().add(userData.id)
            )
        ).also {
            if (it.isFailure)
                return Result.failure(Throwable("ded")) // todo
        }

        return Result.success(Unit)
    }

    suspend fun createGroup(name: String, password: String, color: String): Result<Unit> {
        val dublicate = groupRepository.getGroup(name = name, password = null).getOrNull()
         if (dublicate != null) return Result.failure(Throwable("ded")) // todo

        val userData = userData.value
            ?: return Result.failure(Throwable("ded")) // todo

        val group = Group(
            name = name,
            password = password,
            admin = userData.id,
        )

        groupRepository.addGroup(group)

        userRepository.updateData(
            userId = userData.id,
            data = mapOf(
                "groups" to userData.groups.toMutableList().add(group.id)
            )
        ).also {
            if (it.isFailure)
                return Result.failure(Throwable("ded")) // todo
        }

        return Result.success(Unit)
    }

    private var unsubscribeFromUserChanges: (() -> Unit)? = null
    private var unsubscribeFromUserDataChanges: (() -> Unit)? = null
    private var unsubscribeFromGroupChanges: (() -> Unit)? = null

    init {
        unsubscribeFromUserChanges = authService.listenChanges(this::updateUser)
        unsubscribeFromUserDataChanges = listenUserDataChanges()
    }

    private fun updateUser(user: User?) {
        _user.value = user
    }

    private fun listenUserDataChanges(): (() -> Unit)? {
        if (_user.value === null) {
            return null
        }

        return userRepository.listenChanges(_user.value!!.id) { data ->
            _userData.value = data

            unsubscribeFromGroupChanges = listenGroupChanges()
        }
    }

    private fun listenGroupChanges(): (() -> Unit)? {
        if (userData.value?.groups === null) {
            return null
        }

        val groupIds = userData.value!!.groups

        if (groupIds.isEmpty()) {
            return null
        }

        return groupRepository.listenChanges(groupIds) { processGroupsChange(it) }
    }

    private fun processGroupsChange(repositoryObjectChanges: List<RepositoryObjectChange<Group?>>) {
        for (change in repositoryObjectChanges) {
            if (change.data === null) {
                continue
            }

            when (change.type) {
                RepositoryObjectChange.Type.ADDED,
                RepositoryObjectChange.Type.MODIFIED,
                -> {
                    val index = _groups.indexOfFirst { group -> group.id === change.data!!.id }

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