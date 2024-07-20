package com.lumen1024.groupeventer.pages.groups.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.ui.GroupItem
import com.lumen1024.groupeventer.pages.groups.model.GroupsViewModel


@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val groups by viewModel.userService.groups.collectAsState()
    val userData by viewModel.userService.userData.collectAsState()

    val userGroups by viewModel.userService.userData.collectAsState()

    var selectedGroupId by remember { mutableStateOf<String?>(null) }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }

    LaunchedEffect(groups, selectedGroupId) {
        selectedGroup = groups.find { group -> group.id == selectedGroupId }
    }

    var addDialogOpen by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }

    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(groups, key = { group -> group.id })
            {
                GroupItem(
                    onClick = {
                        isSheetOpen = true
                        selectedGroupId = it.id
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
        if (isSheetOpen) {
            selectedGroup?.let { group ->
                GroupDetailsBottomSheet(
                    onDismiss = { isSheetOpen = false },
                    group = group,
                    onLeave = { viewModel.leaveGroup(group.name) }
                )
            }
        }
    }
}