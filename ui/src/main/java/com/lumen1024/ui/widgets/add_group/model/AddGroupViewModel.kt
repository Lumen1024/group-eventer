package com.lumen1024.ui.widgets.add_group.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.ui.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val userActions: UserActions,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _dismiss = MutableStateFlow(false)
    val dismiss = _dismiss.asStateFlow()
    private fun closeDialog() {
        _dismiss.value = true
    }

    fun resetDismiss() {
        _dismiss.value = false
    }

    fun createGroup(name: String, password: String, color: GroupColor) {
        viewModelScope.launch {
            val result = userActions.createGroup(
                name,
                password,
                color
            ) // todo: maybe color don't work
            if (result.isSuccess) {
                context.showToast("\"$name\" group added")
                closeDialog()
            }
            // TODO: add error handle
        }
    }

    fun joinGroup(name: String, password: String) {
        viewModelScope.launch {
            val r = userActions.joinGroup(name, password)
            if (r.isSuccess) {
                context.showToast("Joined group \"$name\"")
                closeDialog()
            } else if (r.isFailure) {
                context.showToast(r.exceptionOrNull()?.message ?: "Error join group")
            }
            // TODO: add error handle
        }
    }
}