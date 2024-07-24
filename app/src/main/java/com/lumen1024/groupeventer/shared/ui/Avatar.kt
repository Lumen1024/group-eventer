package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    url: Any?,
    size: Dp = 216.dp,
    showBorder: Boolean = false,
) {
    var isLoading by rememberSaveable(url) { mutableStateOf(url != null) }
    var loadingError by remember(url) { mutableStateOf<AsyncImagePainter.State.Error?>(null) }

    val isValidImage by remember(url, loadingError) {
        derivedStateOf { url !== null && loadingError == null }
    }

    Box(
        modifier = Modifier
            .size(size)
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
            .then(modifier)
    ) {
        if (isValidImage) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = url,
                contentDescription = "User avatar",
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator()
                },
                onError = {
                    loadingError = it
                    isLoading = false
                },
                onSuccess = { isLoading = false }
            )
        }
        if (!isValidImage && !isLoading) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.2f),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Default avatar",
            )
        }
    }
}