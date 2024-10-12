package com.lumen1024.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lumen1024.ui.shared.text_field.NameTextField

@Composable
fun Username(
    modifier: Modifier = Modifier,
    username: String,
    onEdited: (String) -> Unit = {},
    showEdit: Boolean = false,
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedUsername by remember { mutableStateOf(username) }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(visible = isEditing) {
            Row(
                modifier = modifier.padding(start = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NameTextField(
                    value = editedUsername,
                    onChange = { editedUsername = it },
                    placeholder = { Text(username) }
                )
                IconButton(onClick = {
                    onEdited(editedUsername)
                    isEditing = false
                }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Edit username"
                    )
                }
            }
        }
        AnimatedVisibility(visible = !isEditing) {
            Row(
                modifier = modifier.padding(start = 40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleLarge,
                )
                if (showEdit) {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit username"
                        )
                    }
                }
            }
        }
    }
}