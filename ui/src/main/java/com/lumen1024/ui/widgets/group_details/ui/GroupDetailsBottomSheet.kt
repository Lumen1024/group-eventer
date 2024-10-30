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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Group
import com.lumen1024.ui.shared.GroupColorBadge
import com.lumen1024.ui.shared.ScalableBottomSheet
import com.lumen1024.ui.widgets.group_details.model.GroupDetailsViewModel


@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsBottomSheet(
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    group: Group,
) {
    val currentUser by viewModel.userStateHolder.userData.collectAsState()
    val admin by viewModel.admin.collectAsState()
    val users by viewModel.users.collectAsState()

    val currentUserIsAdmin by remember {
        derivedStateOf {
            currentUser?.id == admin?.id
        }
    }

    LaunchedEffect(group) {
        viewModel.setGroup(group)
    }

    ScalableBottomSheet(
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding(),
        onDismissRequest = onDismiss,
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
                        GroupColorBadge(color = Color(group.color.hex))
                        Text(text = group.name)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    item(key = "${admin?.id ?: "unknown"}-admin") {
                        GroupUserListItem(
                            modifier = Modifier.fillMaxWidth(1f),
                            userData = admin,
                            textColor = MaterialTheme.colorScheme.primary,
                            showMoreButton = false,
                        )
                    }

                    items(users, key = { it.id }) {
                        GroupUserListItem(
                            modifier = Modifier.fillMaxWidth(1f),
                            userData = it,
                            showMoreButton = currentUserIsAdmin,
                            onRemoveFromGroup = {
                                viewModel.removeUserFromGroup(group.id, it)
                            },
                            onTransferAdministrator = {
                                viewModel.transferAdministrator(group.id, it)
                            }
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    text = "${group.members.size + 1} people", // TODO: res
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                HorizontalDivider()

                Button(
                    modifier = Modifier.padding(vertical = 8.dp),
                    onClick = {
                        viewModel.leaveGroup(group)
                        onDismiss()
                    }
                ) {
                    Text(text = "Leave group") // TODO: res
                }
            }
    }
}


