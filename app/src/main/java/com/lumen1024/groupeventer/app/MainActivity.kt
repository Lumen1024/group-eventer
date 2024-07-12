package com.lumen1024.groupeventer.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.shared.lib.LocalNavController
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import com.lumen1024.groupeventer.shared.model.ScaffoldController
import com.lumen1024.groupeventer.shared.ui.DelegatedScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
                    val navController = rememberNavController()
                    val scaffoldController = ScaffoldController()

                    CompositionLocalProvider(LocalNavController provides navController) {
                        DelegatedScaffold(
                            scaffoldController,
                            modifier = Modifier.systemBarsPadding().statusBarsPadding()
                        ) {
                            MainNavGraph(
                                navController,
                                scaffoldController,
                                modifier = Modifier.fillMaxSize()
                                    .padding(it)
                            )
                        }
                    }
                }
            }
        }
    }
}