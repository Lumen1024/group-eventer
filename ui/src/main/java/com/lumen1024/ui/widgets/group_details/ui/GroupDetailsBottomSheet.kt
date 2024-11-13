package com.lumen1024.ui.widgets.group_details.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.ui.shared.GroupColorBadge
import com.lumen1024.ui.shared.ScalableBottomSheet
import com.lumen1024.ui.widgets.group_details.model.GroupDetailsActions
import com.lumen1024.ui.widgets.group_details.model.GroupDetailsState
import com.lumen1024.ui.widgets.group_details.model.GroupDetailsViewModel


@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsBottomSheet(
    state: GroupDetailsState,
    actions: GroupDetailsActions,
) {

    ScalableBottomSheet(
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding(),
        onDismissRequest = actions::onDismissRequest,
    ) { heightProgressFraction, minHeight ->
        Column(
            modifier = Modifier
                .heightIn(min = minHeight)
                .fillMaxHeight(heightProgressFraction)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GroupColorBadge(color = Color(state.group.color.hex))
                    Text(text = state.group.name)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()


            val admin by remember {
                derivedStateOf {
                    state.users.firstNotNullOf {
                        it.takeIf { it.value == GroupRole.Admin }?.key
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {

                item(key = admin.id) {
                    GroupUserListItem(
                        modifier = Modifier.fillMaxWidth(1f),
                        user = admin,
                        textColor = MaterialTheme.colorScheme.primary,
                        showMoreButton = false,
                    )
                }
                items(state.users.toList(), key = { it.first.id + it.second.toString() }) {
                    GroupUserListItem(
                        modifier = Modifier.fillMaxWidth(1f),
                        user = it.first,
                        showMoreButton = state.showAdminActions,
                        onRemoveFromGroup = {
                            actions.removeUserFromGroup(it.first.id)
                        },
                        onTransferAdministrator = {
                            actions.transferAdministrator(it.first.id)
                        }
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = "${state.group.members.size + 1} people", // TODO: res
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider()

            Button(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = { actions.leaveGroup() }
            ) {
                Text(text = "Leave group") // TODO: res
            }
        }
    }
}

@Composable
fun GroupDetailsBottomSheet(
    onDismiss: () -> Unit,
    group: Group,
) {
    val viewModel: GroupDetailsViewModel =
        hiltViewModel(creationCallback = { factory: GroupDetailsViewModel.Factory ->
            factory.create(group) { onDismiss() }
        }
        )
    val state by viewModel.state.collectAsState()
    val actions: GroupDetailsActions = viewModel

    GroupDetailsBottomSheet(state, actions)
}

@Preview
@Composable
fun GroupDetailsBottomSheetPreview() {
    val state by remember {
        mutableStateOf(GroupDetailsState(
            group = Group(id = ""),
            onDismissRequest = {}
        ))
    }
    val actions: GroupDetailsActions = object : GroupDetailsActions {
        override fun removeUserFromGroup(userId: String) {
            TODO("Not yet implemented")
        }

        override fun transferAdministrator(userId: String) {
            TODO("Not yet implemented")
        }

        override fun leaveGroup() {
            TODO("Not yet implemented")
        }

        override fun onDismissRequest() {
            TODO("Not yet implemented")
        }

    }

    GroupDetailsBottomSheet(state, actions)
}


