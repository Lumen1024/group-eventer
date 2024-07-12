package com.lumen1024.groupeventer.shared.lib

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController =
    compositionLocalOf<NavHostController> { error("No NavHostController found!") }
