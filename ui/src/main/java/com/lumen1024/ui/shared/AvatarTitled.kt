package com.lumen1024.ui.shared

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AvatarTitled(
    modifier: Modifier = Modifier,
    avatarUrl: Uri?,
    username: String?,
    textColor: Color,
) {
    val displayUsername by remember {
        derivedStateOf {
            username.let {
                if (it.isNullOrEmpty()) {
                    return@let "Unknown"
                }
                return@let it
            }
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Avatar(url = avatarUrl, modifier = Modifier.size(42.dp))
        Text(text = displayUsername, color = textColor)
    }
}
