package com.lumen1024.presentation.widgets.group_details.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.UserData
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserDataRepository
import com.lumen1024.domain.usecase.UserStateHolder
import com.lumen1024.groupeventer.shared.lib.showToast
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
    val userStateHolder: UserStateHolder,
    val userActions: UserActions,
) : ViewModel() {
    private val _admin = MutableStateFlow<UserData?>(null)
    val admin = _admin.asStateFlow()

    private val _users = MutableStateFlow(emptyList<UserData>())
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

    fun removeUserFromGroup(groupId: String, user: UserData) {
        viewModelScope.launch {
            userActions.removeUserFromGroup(groupId, user)
        }
    }

    fun transferAdministrator(groupId: String, user: UserData) {
        viewModelScope.launch {
            userActions.transferAdministrator(groupId, user)
        }
    }

    fun leaveGroup(groupId: String) {
        viewModelScope.launch {
            val r = userActions.leaveGroup(groupId)
            if (r.isSuccess) {
                context.showToast("Leaved group \"$groupId\"")
            } else if (r.isFailure) {
                context.showToast(r.exceptionOrNull()?.message ?: "Error leaving group")
            }
        }
    }
}