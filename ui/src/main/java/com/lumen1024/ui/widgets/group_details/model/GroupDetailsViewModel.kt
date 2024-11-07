package com.lumen1024.ui.widgets.group_details.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.User
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserDataRepository
import com.lumen1024.domain.usecase.UserStateHolder
import com.lumen1024.ui.tools.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDataRepository: UserDataRepository,
    private val userActions: UserActions,
    val userStateHolder: UserStateHolder,
) : ViewModel() {
    private val _admin = MutableStateFlow<User?>(null)
    val admin = _admin.asStateFlow()

    private val _users = MutableStateFlow(emptyList<User>())
    val users = _users.asStateFlow()

    fun setGroup(group: Group) {
        viewModelScope.launch {
            launch {
                _users.value = emptyList()
                group.members.keys.forEach { key ->
                    launch {
                        userDataRepository.get(key).onSuccess { user ->
                            _users.value += user
                        }
                    }
                }
            }

            launch {
                userDataRepository.get(group.admin).onSuccess { user ->
                    _admin.value = user
                }.onFailure {
                    _admin.value = null
                }
            }
        }
    }

    fun removeUserFromGroup(groupId: String, user: User) {
        viewModelScope.launch {
            userActions.removeUserFromGroup(groupId, user)
        }
    }

    fun transferAdministrator(groupId: String, user: User) {
        viewModelScope.launch {
            userActions.transferAdministrator(groupId, user)
        }
    }

    fun leaveGroup(group: Group) {
        viewModelScope.launch {
            val r = userActions.leaveGroup(group.id)
            if (r.isSuccess) {
                context.showToast("Leaved from group \"${group.name}\"")
            } else if (r.isFailure) {
                context.showToast(r.exceptionOrNull()?.message ?: "Error leaving group")
            }
        }
    }
}