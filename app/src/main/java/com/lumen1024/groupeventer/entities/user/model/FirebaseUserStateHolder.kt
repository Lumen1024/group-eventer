package com.lumen1024.groupeventer.entities.user.model

import androidx.compose.runtime.mutableStateListOf
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.user_data.model.UserData
import com.lumen1024.groupeventer.entities.user_data.model.UserDataRepository
import com.lumen1024.groupeventer.shared.model.RepositoryObjectChange
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class FirebaseUserStateHolder @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val groupRepository: GroupRepository,
    private val authService: AuthService,
) : UserStateHolder {

    private val _userData = MutableStateFlow<UserData?>(null)
    override val userData = _userData.asStateFlow()

    private val _groups = mutableStateListOf<Group>()
    override val groups get() = MutableStateFlow(_groups.toImmutableList()).asStateFlow()

    private var unsubscribeGroups: (() -> Unit)? = null
    private var unsubscribeUserData: (() -> Unit)? = null

    init {
        authService.listen {
            if (authService.checkAuthorized()) {
                listenUserDataChanges()
                listenGroupChanges()
            } else {
                unsubscribeGroups?.let { it() }
                unsubscribeUserData?.let { it() }
            }
        }
    }

    private fun listenUserDataChanges() {
        userData.value?.let {
            userDataRepository.listen(it.id) { data ->
                _userData.value = data
            }
        }
    }

    private fun listenGroupChanges() {
        userData.value?.groups?.takeIf { it.isNotEmpty() }?.let {
            groupRepository.listenList(it) { changes ->
                processGroupsChange(changes)
            }
        }
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