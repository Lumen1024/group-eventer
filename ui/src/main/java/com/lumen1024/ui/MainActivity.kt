package com.lumen1024.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lumen1024.domain.service.AuthService
import com.lumen1024.ui.app_components.AppContent
import com.lumen1024.ui.config.GroupEventerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authService: AuthService
    private var checkedAuth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition { !checkedAuth }

        CoroutineScope(Dispatchers.IO).launch {
            //authService.refreshToken()
            checkedAuth = true
        }

        // make top and bottom bar transparent
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT, Color.TRANSPARENT
            )
        )

        setContent {
            GroupEventerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    AppContent()
                }
            }
        }
    }
}