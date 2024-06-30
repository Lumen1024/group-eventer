package com.lumen1024.groupeventer.pages.profile.model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage
import com.lumen1024.groupeventer.entities.auth.model.AuthException
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.auth.model.mapAuthExceptionToMessage
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.entities.user.model.UserRepository
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebase: Firebase,
    private val authService: AuthService,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val avatarsRef = firebase.storage.reference.child("avatars")

    private val _username = MutableStateFlow(firebase.auth.currentUser?.displayName)
    val username = _username.asStateFlow()

    private val _avatarUrl = MutableStateFlow(firebase.auth.currentUser?.photoUrl)
    val avatarUrl = _avatarUrl.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val groups = _userData.asStateFlow()

    init {
        viewModelScope.launch {
            if (firebase.auth.currentUser !== null) {
                val result = userRepository.getData(firebase.auth.currentUser!!.uid)

                if (result.isSuccess) {
                    _userData.value = result.getOrNull()
                }
            }
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            _username.value = firebase.auth.currentUser?.displayName.toString()

            val result = authService.updateUser(name = name)
            val exception = result.exceptionOrNull()

            if (result.isFailure && exception !== null) {
                if (exception is AuthException) {
                    context.showToast(context.resources.getString(exception.mapAuthExceptionToMessage()))
                }
            }

            _username.value = firebase.auth.currentUser?.displayName.toString()
        }
    }

    fun updateAvatar(avatarUrl: Uri) {
        viewModelScope.launch {
            if (firebase.auth.currentUser !== null) {
                _avatarUrl.value = avatarUrl

                try {
                    val uploadTask =
                        avatarsRef.child(firebase.auth.currentUser!!.uid).putFile(avatarUrl).await()

                    val downloadUri = uploadTask.storage.downloadUrl.await()


                    val result = authService.updateUser(avatarUrl = downloadUri)
                    val exception = result.exceptionOrNull()

                    if (result.isFailure && exception !== null) {
                        if (exception is AuthException) {
                            context.showToast(context.resources.getString(exception.mapAuthExceptionToMessage()))
                        }
                    }

                    _avatarUrl.value = Uri.parse(firebase.auth.currentUser?.photoUrl.toString())
                } catch (e: Exception) {
                    context.showToast("Error when setting avatar")
                }
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            authService.logout()
        }
    }
}