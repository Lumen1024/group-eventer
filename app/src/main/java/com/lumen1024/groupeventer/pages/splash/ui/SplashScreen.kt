package com.lumen1024.groupeventer.pages.splash.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.shared.config.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    fun goNext(navController: NavController) {
        if (authService.checkAuthorized())
            navController.navigate(Screen.Home.route) {
                // Ensure the SplashScreen is removed from the back stack
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        else
            navController.navigate(Screen.Auth.route) {
                // Ensure the SplashScreen is removed from the back stack
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    val viewmodel: SplashViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        visible = true
        kotlinx.coroutines.delay(500) // 1 second for the fade-out animation
        // Navigate to HomeScreen
        viewmodel.goNext(navController)

    }


    AnimatedVisibility(
        visible = visible,
        //exit = fadeOut(animationSpec = tween(durationMillis = 2000)),
        enter = fadeIn(animationSpec = tween(durationMillis = 500))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}