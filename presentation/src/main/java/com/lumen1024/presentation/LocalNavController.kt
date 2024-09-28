package com.lumen1024.presentation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController =
    compositionLocalOf<NavHostController> { error("No NavHostController found!") }
