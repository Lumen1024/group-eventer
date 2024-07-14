package com.lumen1024.groupeventer.pages.groups.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.user.model.UserService
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val userService: UserService,
) : ViewModel() {

    fun leaveGroup(name: String) {
        viewModelScope.launch {
            val r = userService.leaveGroup(name)
            if (r.isSuccess) {
                context.showToast("Leaved group \"$name\"")
            } else if (r.isFailure) {
                context.showToast(r.exceptionOrNull()?.message ?: "Error leaving group")
            }
            val ded = 0
        }
    }
}