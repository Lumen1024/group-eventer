package com.lumen1024.groupeventer.pages.groups.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.shared.config.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    fun logout(navController: NavController) {
        viewModelScope.launch {
            authService.logout()
            navController.navigate(Screen.Auth.route)
        }
    }
}

@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {

}