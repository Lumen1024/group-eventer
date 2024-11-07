package com.lumen1024.ui.screen.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.data.AuthException
import com.lumen1024.domain.usecase.AuthService
import com.lumen1024.domain.usecase.UserDataRepository
import com.lumen1024.domain.usecase.UserStateHolder
import com.lumen1024.ui.navigation.Navigator
import com.lumen1024.ui.tools.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val navigator: Navigator,
    private val authService: AuthService,
    private val userDataRepository: UserDataRepository,
    val userStateHolder: UserStateHolder,
) : ViewModel() {
    fun updateName(name: String) {
        viewModelScope.launch {
            userDataRepository.update(
                this@ProfileViewModel.user.user.value?.id ?: return@launch,
                mapOf(
                    "name" to name
                )
            ).onFailure {
                val messageResId = when (it) {
                    is AuthException -> it.message
                    else -> AuthException.Unknown("Error when updating name").message
                }

                context.showToast(messageResId ?: "error") // TODO: res?
            }
        }
    }

    fun updateAvatar(imageUri: Uri) {
        viewModelScope.launch {
            try {
                val userId = this@ProfileViewModel.user.user.value?.id ?: return@launch

                val avatarUrl = userDataRepository.uploadAvatar(
                    userId, imageUri.toString()
                ).onFailure {
                    val messageResId = when (it) {
                        is AuthException -> it.message
                        else -> {
                            val exception =
                                AuthException.Unknown("Error when loading avatar image to server")

                            Log.e("ProfileViewModel", exception.message, exception)

                            exception.message
                        }
                    }

                    context.showToast(messageResId ?: "error")
                }.getOrNull() ?: return@launch

                userDataRepository.update(
                    userId, mapOf(
                        "avatarUrl" to avatarUrl.toString()
                    )
                ).onFailure {
                    val messageResId = when (it) {
                        is AuthException -> it.message
                        else -> {
                            val exception =
                                AuthException.Unknown("Error when updating avatar")

                            Log.e("ProfileViewModel", exception.message, exception)

                            exception.message
                        }
                    }

                    context.showToast(messageResId ?: "error")
                }
            } catch (e: Exception) {
                context.showToast("Error when setting avatar")
            }
        }
    }
}