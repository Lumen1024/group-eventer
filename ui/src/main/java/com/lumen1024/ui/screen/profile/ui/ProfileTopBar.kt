package com.lumen1024.ui.screen.profile.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Profile",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = { onLogout() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "")
            }
        }
    )
}