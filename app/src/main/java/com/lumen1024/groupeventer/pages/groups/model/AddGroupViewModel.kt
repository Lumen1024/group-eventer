package com.lumen1024.groupeventer.pages.groups.model

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.user.model.UserService
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val userService: UserService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _dismiss = MutableStateFlow(false)
    val dismiss = _dismiss.asStateFlow()
    private fun closeDialog() {
        _dismiss.value = true
    }

    fun joinGroup(name: String, password: String) {
        viewModelScope.launch {
            val r = userService.joinGroup(name, password)
            if (r.isSuccess) {
                context.showToast("group added")
                closeDialog()
            }
            // todo: add error handle
        }
    }

    fun createGroup(name: String, password: String, color: Color) {
        viewModelScope.launch {
            val r = userService.createGroup(name, password, color.toString()) // todo: maybe color don't work
            if (r.isSuccess) {
                context.showToast("group added")
                closeDialog()
            }
            // todo: add error handle
        }
    }
}