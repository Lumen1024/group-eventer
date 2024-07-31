package com.lumen1024.groupeventer.widgets.group_details.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.entities.user.model.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
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
}