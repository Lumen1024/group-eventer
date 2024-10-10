package com.lumen1024.ui.app_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lumen1024.ui.getCurrentScreenAsState
import com.lumen1024.ui.shared.NavBar

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val showBottomBar by remember { derivedStateOf { currentScreen in com.lumen1024.ui.HOME_PAGE_BOTTOM_BAR_ITEMS } }
    AnimatedVisibility(
        showBottomBar,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 200 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { 200 })
    ) {
        NavBar(
            modifier = modifier,
            items = com.lumen1024.ui.HOME_PAGE_BOTTOM_BAR_ITEMS,
            navController = navController,
        )
    }
}