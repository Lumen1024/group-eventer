package com.lumen1024.groupeventer.app.scaffold_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.pages.groups.ui.AddGroupDialog
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FloatingButtonViewModel @Inject constructor(

) : ViewModel()

@Composable
fun AppFloatingButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentScreen by navController.getCurrentScreenAsState()
    var showAddGroupDialog by remember { mutableStateOf(false) }

    val showAddButton by remember {
        derivedStateOf {
            currentScreen in listOf( // todo: move to const?
                Screen.Groups,
                Screen.Events
            )
        }
    }
    val action = {
        if (currentScreen == Screen.Events)
            navController.navigate(Screen.CreateEvent)
        else if (currentScreen == Screen.Groups)
            showAddGroupDialog = true
    }
    AnimatedVisibility(
        visible = showAddButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        FloatingActionButton(
            modifier = modifier.padding(16.dp),
            onClick = action,
        ) {
            Icon(Icons.Default.Add, "")
        }
    }

    if (showAddGroupDialog)
        AddGroupDialog(onDismiss = { showAddGroupDialog = false })
}