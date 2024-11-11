package com.lumen1024.ui.screen.groups

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.shared.GroupItem
import com.lumen1024.ui.widgets.group_details.ui.GroupDetailsBottomSheet


@Composable
fun GroupsScreen(
    state: GroupsScreenState,
    actions: GroupsScreenActions,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(state.groups, key = { group -> group.id })
        {
            GroupItem(
                onClick = { actions.onGroupClicked(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                group = it,
            )
            HorizontalDivider()
        }
    }

    if (state.detailsBottomSheetState is DetailsBottomSheetState.Opened) {
        GroupDetailsBottomSheet(
            onDismiss = state.detailsBottomSheetState.onDismiss,
            group = state.detailsBottomSheetState.group,
        )
    }
}

@Composable
fun GroupsScreenWithVM() {
    val viewModel: GroupsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val actions: GroupsScreenActions = viewModel

    GroupsScreen(state, actions)
}

@Preview
@Composable
fun GroupsScreenPreview() {
    val state by remember {
        mutableStateOf(
            GroupsScreenState(

            )
        )
    }
    val actions: GroupsScreenActions = object : GroupsScreenActions {
        override fun onGroupClicked(group: Group) {
            TODO("Not yet implemented")
        }

    }

    GroupsScreen(state, actions)
}