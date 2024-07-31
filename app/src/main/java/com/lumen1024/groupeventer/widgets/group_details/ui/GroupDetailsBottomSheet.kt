package com.lumen1024.groupeventer.widgets.group_details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.ui.GroupColorBadge
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.shared.ui.Avatar
import com.lumen1024.groupeventer.widgets.group_details.model.GroupDetailsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsBottomSheet(
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    group: Group,
    onLeave: () -> Unit,
) {
    val admin by viewModel.admin.collectAsState()
    val users by viewModel.users.collectAsState()

    LaunchedEffect(group.id) {
        viewModel.setGroup(group)
    }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
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
                    GroupColorBadge(color = group.color.color)
                    Text(text = group.name)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .heightIn(min = 245.dp)
                    .let {
                        if (sheetState.currentValue == SheetValue.Expanded) {
                            it.weight(1f)
                        } else {
                            it
                        }
                    }
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item(key = "${admin?.id ?: "unknown"}-admin") {
                    UserListItem(
                        userData = admin,
                        textColor = MaterialTheme.colorScheme.primary
                    )
                }

                items(users, key = { it.id }) {
                    UserListItem(userData = it)
                }
            }

            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = "${group.members.size + 1} people",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider()

            Button(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    onLeave()
                    onDismiss()
                }
            ) {
                Text(text = "Leave group")
            }
        }
    }
}

@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    userData: UserData?,
    textColor: Color = Color.Unspecified,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Avatar(url = userData?.avatarUrl, size = 42.dp)
        Text(text = userData.let {
            if (it == null || it.name.isEmpty()) {
                return@let "Unknown"
            }

            return@let it.name
        }, color = textColor)
    }
}