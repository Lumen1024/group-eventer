package com.lumen1024.groupeventer.pages.groups.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
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
                        // TODO smth with hide and move leave on group page?
//                        viewModel.toggleGroupHide(it.id)
                        viewModel.leaveGroup(it.name)
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