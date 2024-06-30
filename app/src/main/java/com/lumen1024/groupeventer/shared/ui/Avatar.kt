package com.lumen1024.groupeventer.shared.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage

@Composable
fun Avatar(modifier: Modifier = Modifier, url: Any?) {
    var isFirstLoad by remember { mutableStateOf(false) }
    var loadingError by remember { mutableStateOf<AsyncImagePainter.State.Error?>(null) }

    val isValidImage by remember { derivedStateOf { url !== null && loadingError === null } }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(200.dp)
            .then(modifier)
    ) {
        AnimatedVisibility(visible = isValidImage) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = url,
                contentDescription = "User avatar",
                contentScale = ContentScale.FillBounds,
                loading = {
                    if (isFirstLoad) {
                        CircularProgressIndicator()
                    }
                },
                onError = { loadingError = it },
                onSuccess = { isFirstLoad = false }
            )
        }
        AnimatedVisibility(visible = !isValidImage) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Default avatar",
            )
        }
    }
}