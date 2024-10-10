package com.lumen1024.data.implementation

import androidx.compose.runtime.mutableStateListOf
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.RepositoryObjectChange
import com.lumen1024.domain.data.UserData
import com.lumen1024.domain.usecase.AuthService
import com.lumen1024.domain.usecase.GroupRepository
import com.lumen1024.domain.usecase.UserDataRepository
import com.lumen1024.domain.usecase.UserStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FirebaseUserStateHolder @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val groupRepository: GroupRepository,
    private val authService: AuthService,
) : UserStateHolder {

    private val _userData = MutableStateFlow<UserData?>(null)
    override val userData = _userData.asStateFlow()

    private val _groups = mutableStateListOf<Group>() // TODO: is android mutable state?
    override val groups get() = MutableStateFlow(_groups.toList()).asStateFlow() // TODO: toImmutableList replaced?

    private var unsubscribeAuth: (() -> Unit)? = null
    private var unsubscribeGroups: (() -> Unit)? = null
    private var unsubscribeUserData: (() -> Unit)? = null

    init {
        listenChanges()
    }

    private fun listenChanges() {
        unsubscribeAuth?.let { it() }
        unsubscribeAuth = authService.listen {
            unsubscribeUserData?.let { it() }
            unsubscribeUserData = listenUserDataChanges()
        }.getOrNull()
    }

    private fun listenUserDataChanges(): (() -> Unit)? {
        val userId = userData.value?.id ?: authService.userId ?: return null

        return userDataRepository.listen(userId) { data ->
            _userData.value = data

            unsubscribeGroups?.let { it() }
            unsubscribeGroups = listenGroupChanges()
        }.getOrNull()
    }

    private fun listenGroupChanges(): (() -> Unit)? {
        // Remove groups that no more in user data
        userData.value?.groups.let {
            if (it !== null) {
                _groups.removeIf { group -> group.id !in it }
            }
        }

        userData.value?.groups?.takeIf { it.isNotEmpty() }?.let { groups ->
            return groupRepository.listenList(groups) { changes ->
                processGroupsChange(changes)
            }.getOrNull()
        } ?: return null
    }

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