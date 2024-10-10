package com.lumen1024.ui.screen.groups

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.GroupItem
import com.lumen1024.ui.widgets.add_group.ui.AddGroupDialog
import com.lumen1024.ui.widgets.group_details.ui.GroupDetailsBottomSheet


@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val groups by viewModel.userStateHolder.groups.collectAsState()

    var selectedGroupId by remember { mutableStateOf<String?>(null) }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }

    LaunchedEffect(groups, selectedGroupId) {
        selectedGroup = groups.find { group -> group.id == selectedGroupId }
    }

    val isSheetOpen by remember { derivedStateOf { selectedGroup !== null } }

    var addDialogOpen by remember { mutableStateOf(false) }

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
                    onDismiss = { selectedGroupId = null },
                    group = group,
                )
            }
        }
    }
}