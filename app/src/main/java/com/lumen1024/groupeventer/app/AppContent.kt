package com.lumen1024.groupeventer.app

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.app.scaffold_components.AppBottomBar
import com.lumen1024.groupeventer.app.scaffold_components.AppDrawerContent
import com.lumen1024.groupeventer.app.scaffold_components.AppFloatingButton
import com.lumen1024.groupeventer.app.scaffold_components.AppTopBar
import com.lumen1024.groupeventer.app.scaffold_components.DrawerViewModel
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val currentScreen by navController.getCurrentScreenAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerViewModel: DrawerViewModel = hiltViewModel()

    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, drawerViewModel.drawerController.drawerChannel) {
        drawerViewModel.drawerController.drawerChannel.receiveAsFlow().collect { value ->
            if (activity?.isFinishing == true) return@collect
            if (drawerState.currentValue != value) drawerState.apply { if (value == DrawerValue.Closed) close() else open() }
        }
    }

    ModalNavigationDrawer(
        drawerContent = { AppDrawerContent() },
        drawerState = drawerState,
        gesturesEnabled = true,
        ) {
        Scaffold(
            //modifier = Modifier.systemPadding(),
            topBar = { AppTopBar(navController = navController) },
            bottomBar = { AppBottomBar(navController = navController) },
            floatingActionButton = { AppFloatingButton(navController = navController) },
        ) {
            MainNavGraph(
                navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
        }
    }

}




