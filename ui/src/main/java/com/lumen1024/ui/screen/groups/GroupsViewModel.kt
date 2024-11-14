package com.lumen1024.ui.screen.groups

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.usecase.GetCurrentUserGroupsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Immutable
data class GroupsScreenState(
    val groups: List<Group> = emptyList(),
    val groupDetailsBottomSheetState: GroupDetailsBottomSheetState = GroupDetailsBottomSheetState.Closed,
)

@Immutable
interface GroupsScreenActions {
    fun onGroupClicked(group: Group)
}

@Immutable
sealed interface GroupDetailsBottomSheetState {
    object Closed : GroupDetailsBottomSheetState

    data class Opened(
        val onDismiss: () -> Unit,
        val group: Group,
    ) : GroupDetailsBottomSheetState
}


@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
) : ViewModel(), GroupsScreenActions {

    private val _state = MutableStateFlow(GroupsScreenState())
    val state: StateFlow<GroupsScreenState> = _state.asStateFlow()

    override fun onGroupClicked(group: Group) {
        _state.value = _state.value.copy(
            groupDetailsBottomSheetState = GroupDetailsBottomSheetState.Opened(
                onDismiss = {
                    _state.value = _state.value.copy(
                        groupDetailsBottomSheetState = GroupDetailsBottomSheetState.Closed
                    )
                },
                group = group,
            )
        )
    }

    init {
        viewModelScope.launch {
            getCurrentUserGroupsUseCase().collect { groups ->
                _state.value = _state.value.copy(groups = groups)
            }
        }
    }
}