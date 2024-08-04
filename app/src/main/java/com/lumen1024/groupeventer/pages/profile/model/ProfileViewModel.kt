package com.lumen1024.groupeventer.pages.profile.model

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.auth.model.AuthException
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.auth.model.mapToResource
import com.lumen1024.groupeventer.entities.user.model.UserDataRepository
import com.lumen1024.groupeventer.entities.user.model.UserStateHolder
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.showToast
import com.lumen1024.groupeventer.shared.model.Navigator
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
                userStateHolder.userData.value?.id ?: return@launch,
                mapOf(
                    "name" to name
                )
            ).onFailure {
                val messageResId = when (it) {
                    is AuthException -> it.mapToResource()
                    else -> AuthException.Unknown("Error when updating name").mapToResource()
                }

                context.showToast(context.resources.getString(messageResId))
            }
        }
    }

    fun updateAvatar(imageUri: Uri) {
        viewModelScope.launch {
            try {
                val userId = userStateHolder.userData.value?.id ?: return@launch

                val avatarUrl = userDataRepository.uploadAvatar(
                    userId, imageUri
                ).onFailure {
                    val messageResId = when (it) {
                        is AuthException -> it.mapToResource()
                        else -> {
                            val exception =
                                AuthException.Unknown("Error when loading avatar image to server")

                            Log.e("ProfileViewModel", exception.message, exception)

                            exception.mapToResource()
                        }
                    }

                    context.showToast(context.resources.getString(messageResId))
                }.getOrNull() ?: return@launch

                userDataRepository.update(
                    userId, mapOf(
                        "avatarUrl" to avatarUrl.toString()
                    )
                ).onFailure {
                    val messageResId = when (it) {
                        is AuthException -> it.mapToResource()
                        else -> {
                            val exception =
                                AuthException.Unknown("Error when updating avatar")

                            Log.e("ProfileViewModel", exception.message, exception)

                            exception.mapToResource()
                        }
                    }

                    context.showToast(context.resources.getString(messageResId))
                }
            } catch (e: Exception) {
                context.showToast("Error when setting avatar")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authService.logout()

            if (!authService.checkAuthorized()) {
                navigator.tryNavigateTo(
                    route = Screen.Auth,
                    // TODO: ?
                    // FIXME:  
                )
            }
        }
    }
}