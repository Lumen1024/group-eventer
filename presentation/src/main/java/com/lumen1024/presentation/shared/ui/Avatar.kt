package com.lumen1024.presentation.shared.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun Avatar(
    modifier: Modifier,
    url: Any?,
    onClick: (() -> Unit)? = null,
    showBorder: Boolean = false,
) {

    Box(
        modifier = modifier
            .then(
                if (showBorder) Modifier
                    .border(
                        BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
                        shape = CircleShape
                    )
                    .padding(8.dp)
                else Modifier
            )
            .clip(CircleShape)
            .then(
                if (onClick != null) Modifier
                    .clickable(
                        role = Role.Button,
                        onClick = onClick
                    )
                else Modifier
            )

    ) {
        if (url != null) {
            SubcomposeAsyncImage(
                model = url,
                contentDescription = "User avatar",
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(modifier.fillMaxSize())
                },
                error = {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.2f),
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Default avatar",
                    )
                },
            )
        }
    }
}