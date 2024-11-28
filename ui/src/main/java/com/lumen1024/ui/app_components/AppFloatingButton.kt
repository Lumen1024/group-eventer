package com.lumen1024.ui.app_components

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.lumen1024.ui.navigation.Screen
import com.lumen1024.ui.tools.getCurrentScreenAsState
import com.lumen1024.ui.widgets.add_group.ui.AddGroupDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FloatingButtonViewModel @Inject constructor(
    //val userStateHolder: UserStateHolder // TODO: ref
) : ViewModel()

@Composable
fun AppFloatingButton(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: FloatingButtonViewModel = hiltViewModel()
) {
    val currentScreen by navController.getCurrentScreenAsState()

    //val hasGroups by remember { derivedStateOf { !viewModel.userStateHolder.groups.value.isEmpty() } }

    var showAddGroupDialog by remember { mutableStateOf(false) }

    val showAddButton by remember {
        derivedStateOf {
            when (currentScreen) {
                Screen.Groups -> true
                Screen.Events -> true//hasGroups
                else -> false
            }
        }
    }

    val action = {
        if (currentScreen == Screen.Events) {
            navController.navigate(Screen.CreateEvent)
        } else if (currentScreen == Screen.Groups) {
            showAddGroupDialog = true
        }
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
        AddGroupDialog(onDismissRequest = { showAddGroupDialog = false })
}