package com.lumen1024.ui.widgets.group_details.ui

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.UserData
import com.lumen1024.ui.AvatarTitled

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupUserListItem(
    modifier: Modifier = Modifier,
    userData: UserData?,
    textColor: Color = Color.Unspecified,
    showMoreButton: Boolean = false,
    onRemoveFromGroup: () -> Unit = {},
    onTransferAdministrator: () -> Unit = {},
) {
    var moreOptionsMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .combinedClickable(
                onLongClick = { moreOptionsMenuExpanded = true },
                onClick = { } // TODO: implement go to user profile on just click?
            )
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AvatarTitled(
            avatarUrl = userData?.avatarUrl?.let { Uri.parse(it) },
            username = userData?.name,
            textColor = textColor
        )

        if (showMoreButton) {
            Box {
                IconButton(onClick = { moreOptionsMenuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options" // TODO: res
                    )
                }
                GroupUserActionsDropdown(
                    expanded = moreOptionsMenuExpanded,
                    onDismissRequest = { moreOptionsMenuExpanded = false },
                    onRemoveFromGroup = onRemoveFromGroup,
                    onTransferAdministrator = onTransferAdministrator
                )
            }
        }
    }
}

