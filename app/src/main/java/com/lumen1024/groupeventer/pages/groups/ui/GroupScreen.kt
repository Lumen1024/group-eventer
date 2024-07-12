package com.lumen1024.groupeventer.pages.groups.ui

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.ui.GroupItem
import com.lumen1024.groupeventer.pages.groups.model.GroupsViewModel
import com.lumen1024.groupeventer.shared.config.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val groups by viewModel.userService.groups.collectAsState()
    val userData by viewModel.userService.userData.collectAsState()

    val userGroups by viewModel.userService.userData.collectAsState()

    var selected by remember { mutableStateOf<Group?>(null) }

    var addDialogOpen by remember { mutableStateOf(false) }
    var sheetOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Screen.Home.Groups.label),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                    }
                },
                actions = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "")
                    Spacer(modifier = Modifier.width(16.dp))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addDialogOpen = true
            }) {
                Icon(Icons.Default.Add, "")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
        ) {
            items(groups, key = { group -> group.id })
            {
                GroupItem(
                    onClick = {
                        viewModel.toggleGroupHide(it.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    group = it,
                )
                HorizontalDivider()
            }
        }

        if (addDialogOpen) {
            AddGroupDialog(
                onDismiss = { addDialogOpen = false }
            )
        }
        if (sheetOpen) {
            GroupDetailsBottomSheet()
        }
    }
}