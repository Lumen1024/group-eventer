package com.lumen1024.ui.screen.create_event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateEventTopBar(
    title: String,
    onBackClick: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "")
        }
        Text(
            text = title.ifEmpty { "New Event" },
            style = MaterialTheme.typography.titleLarge
        )
        TextButton(modifier = Modifier.padding(horizontal = 8.dp),
            onClick = { onSaveClicked() }
        ) {
            Text(text = "save")
        }
    }
}