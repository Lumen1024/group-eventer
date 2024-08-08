package com.lumen1024.groupeventer.widgets.group_details.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupUserActionsDropdown(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRemoveFromGroup: () -> Unit,
    onTransferAdministrator: () -> Unit,
) {
    val doActionAndDismiss = { action: () -> Unit ->
        action()
        onDismissRequest()
    }

    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Remove from group") }, // TODO: res
            onClick = {
                doActionAndDismiss { onRemoveFromGroup() }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.RemoveCircle,
                    contentDescription = "Remove icon" // TODO: res
                )
            }
        )
        DropdownMenuItem(
            text = { Text("Transfer administrator") }, // TODO: res
            onClick = {
                doActionAndDismiss { onTransferAdministrator() }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = "Transfer administrator icon" // TODO: res
                )
            }
        )
    }
}
