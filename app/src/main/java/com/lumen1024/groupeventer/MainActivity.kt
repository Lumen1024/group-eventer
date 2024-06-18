package com.lumen1024.groupeventer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lumen1024.groupeventer.screen.MainNavGraph
import com.lumen1024.groupeventer.ui.theme.GroupEventerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            GroupEventerTheme {
                MainNavGraph()
            }
        }
    }
}