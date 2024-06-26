package com.lumen1024.groupeventer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        enableEdgeToEdge()
        setContent {
            GroupEventerTheme {
                MainNavGraph()
            }
        }
    }
}