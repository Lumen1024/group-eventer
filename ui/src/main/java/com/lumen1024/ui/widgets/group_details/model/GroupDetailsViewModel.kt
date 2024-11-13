package com.lumen1024.ui.widgets.group_details.model

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.domain.data.User
import com.lumen1024.domain.usecase.GetUsersByGroupUseCase
import com.lumen1024.domain.usecase.LeaveGroupUseCase
import com.lumen1024.domain.usecase.RemoveUserFromGroupUseCase
import com.lumen1024.domain.usecase.TransferGroupAdminUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
data class GroupDetailsState(
    val group: Group,
    val onDismissRequest: () -> Unit,
    val users: Map<User, GroupRole> = emptyMap(),
    val showAdminActions: Boolean = false,
)

@Immutable
interface GroupDetailsActions {
    fun removeUserFromGroup(userId: String)
    fun transferAdministrator(userId: String)
    fun leaveGroup()
    fun onDismissRequest()
}

@HiltViewModel
class GroupDetailsViewModel @AssistedInject constructor(
    @Assisted val group: Group,
    @Assisted val onDismissRequest: () -> Unit,
    private val getUsersByGroupUseCase: GetUsersByGroupUseCase,
    private val transferGroupAdminUseCase: TransferGroupAdminUseCase,
    private val removeUserFromGroupUseCase: RemoveUserFromGroupUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase,
) : ViewModel(), GroupDetailsActions {
    private val _state = MutableStateFlow(
        GroupDetailsState(
            group = group,
            onDismissRequest = onDismissRequest,
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUsersByGroupUseCase(group).collect {
                _state.value = _state.value.copy(users = it)
            }
        }
    }

    override fun removeUserFromGroup(userId: String) {
        viewModelScope.launch {
            removeUserFromGroupUseCase(group.id, userId)
        }
    }

    override fun transferAdministrator(userId: String) {
        viewModelScope.launch {
            transferGroupAdminUseCase(group.id, userId)
        }
    }

    override fun leaveGroup() {
        viewModelScope.launch {
            leaveGroupUseCase(group.id)
            onDismissRequest()
        }
    }

    override fun onDismissRequest() = this@GroupDetailsViewModel.onDismissRequest.invoke()

    @AssistedFactory
    interface Factory {
        fun create(group: Group, onDismissRequest: () -> Unit): GroupDetailsViewModel
    }
}