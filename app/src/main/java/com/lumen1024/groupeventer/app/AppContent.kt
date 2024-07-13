package com.lumen1024.groupeventer.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lumen1024.groupeventer.app.config.bottomBarItems
import com.lumen1024.groupeventer.app.navigation.MainNavGraph
import com.lumen1024.groupeventer.pages.groups.ui.AddGroupDialog
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.LocalNavController
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import com.lumen1024.groupeventer.shared.model.ScaffoldController
import com.lumen1024.groupeventer.shared.ui.DelegatedScaffold
import com.lumen1024.groupeventer.shared.ui.NavBar
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Composable
fun ApplicationContentOld() {
    val navController = rememberNavController()
    val scaffoldController = ScaffoldController()

    CompositionLocalProvider(LocalNavController provides navController) {
        DelegatedScaffold(
            scaffoldController,
            modifier = Modifier
                .systemBarsPadding()
                .statusBarsPadding()
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

@Composable
fun AppContent() {
    val navController = rememberNavController()



    Scaffold(
        modifier = Modifier.systemPadding(),
        bottomBar = { AppBottomBar(navController = navController) },
        floatingActionButton = { AppFloatingButton(navController = navController)}
    ) {
        MainNavGraph(
            navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {

}

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val showBottomBar by remember { derivedStateOf { currentScreen in bottomBarItems } }
    if (showBottomBar) {
        NavBar(
            items = bottomBarItems,
            startDestination = Screen.Events,
            navController = navController,
        )
    }
}

@HiltViewModel
class FloatingButtonViewModel @Inject constructor(

) : ViewModel() {

}

@Composable
fun AppFloatingButton(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val showAddButton by remember { derivedStateOf { currentScreen == Screen.Groups } }
    var showAddGroupDialog by remember { mutableStateOf(false) }

    if (showAddButton)
        FloatingActionButton(onClick = {
            showAddGroupDialog = true
        }) {
            Icon(Icons.Default.Add, "")
        }
    if (showAddGroupDialog)
        AddGroupDialog(onDismiss = { showAddGroupDialog = false })
}

@Composable
fun Modifier.systemPadding(): Modifier {
    return this
        .systemBarsPadding()
        .statusBarsPadding()
}