package com.lumen1024.ui.screen.profile

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.domain.usecase.ChangeUserAvatarUseCase
import com.lumen1024.domain.usecase.ChangeUserNameUseCase
import com.lumen1024.domain.usecase.GetCurrentUserUseCase
import com.lumen1024.ui.ToastService
import com.lumen1024.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class ProfileUiState(
    val avatarUri: Uri? = null,
    val name: String = "",
)

@Immutable
interface ProfileUiActions {
    fun updateName(name: String)
    fun updateAvatar(imageUri: Uri)
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val toastService: ToastService,
    private val navigator: Navigator,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val changeUserNameUseCase: ChangeUserNameUseCase,
    private val changeUserAvatarUseCase: ChangeUserAvatarUseCase,
) : ViewModel(), ProfileUiActions {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {

            getCurrentUserUseCase().collect {
                if (it == null) throw Exception("User not found")

                _state.value = _state.value.copy(
                    avatarUri = it.avatarUrl?.let { Uri.parse(it) },
                    name = it.name,
                )
            }

        }
    }

    override fun updateName(name: String) {
        viewModelScope.launch { changeUserNameUseCase(name) }
    }

    override fun updateAvatar(imageUri: Uri) {
        viewModelScope.launch { changeUserAvatarUseCase(imageUri.toString()) }
    }
}