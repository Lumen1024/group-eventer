package com.lumen1024.ui.widgets.add_group.model

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.usecase.CreateGroupUseCase
import com.lumen1024.domain.usecase.JoinGroupUseCase
import com.lumen1024.ui.ToastService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddGroupUiState(
    val onDismissRequest: () -> Unit,
    val addGroupMethod: AddGroupMethod = AddGroupMethod.Join,
    val name: String = "",
    val password: String = "",
    val color: GroupColor = GroupColor.entries[0],
    val isLoading: Boolean = false,
)

enum class AddGroupMethod {
    Join,
    Create,
}

interface AddGroupUiActions {
    fun changeGroupAdditionMethod(method: AddGroupMethod)
    fun onConfirm()
    fun onDismissRequest()
}

@HiltViewModel(assistedFactory = AddGroupViewModel.Factory::class)
class AddGroupViewModel @AssistedInject constructor(
    @Assisted private val onDismissRequest: () -> Unit,
    private val createGroupUseCase: CreateGroupUseCase,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val toastService: ToastService,
) : ViewModel(), AddGroupUiActions {
    private val _state = MutableStateFlow(AddGroupUiState(onDismissRequest))
    val state = _state.asStateFlow()


    override fun changeGroupAdditionMethod(method: AddGroupMethod) {
        _state.value = _state.value.copy(addGroupMethod = method)
    }

    override fun onConfirm() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (state.value.addGroupMethod) {
                AddGroupMethod.Join -> joinGroup(
                    state.value.name,
                    state.value.password
                )

                AddGroupMethod.Create -> createGroup(
                    state.value.name,
                    state.value.password,
                    state.value.color
                )
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }


    private suspend fun createGroup(
        name: String,
        password: String,
        color: GroupColor
    ): Result<Unit> {
        val result = createGroupUseCase(
            name,
            password,
            color
        ) // todo: maybe color don't work
        if (result.isSuccess) {
            toastService.showToast("\"$name\" group added")
            onDismissRequest()
        }
        return result
    }

    private suspend fun joinGroup(name: String, password: String): Result<Unit> {
        val r = joinGroupUseCase(name, password)
        if (r.isSuccess) {
            toastService.showToast("Joined group \"$name\"")
            onDismissRequest()
        } else if (r.isFailure) {
            toastService.showToast(r.exceptionOrNull()?.message ?: "Error join group")
        }
        // TODO: add error handle
        return r
    }

    override fun onDismissRequest() = this@AddGroupViewModel.onDismissRequest.invoke()

    @AssistedFactory
    interface Factory {
        fun create(onDismissRequest: () -> Unit): AddGroupViewModel
    }

    companion object {

        @Composable
        private fun create(
            onDismissRequest: () -> Unit
        ): AddGroupViewModel {
            return hiltViewModel { factory: Factory ->
                factory.create(onDismissRequest)
            }
        }
    }
}