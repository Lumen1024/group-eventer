package com.lumen1024.groupeventer.app.scaffold_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.pages.groups.ui.AddGroupDialog
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState

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