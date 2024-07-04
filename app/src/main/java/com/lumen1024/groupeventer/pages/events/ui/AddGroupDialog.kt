package com.lumen1024.groupeventer.pages.events.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt
import com.lumen1024.groupeventer.entities.group.ui.randomColor

@Composable
fun AddGroupDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, password: String) -> Unit,
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .size(width = 250.dp, height = 40.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                val tabModifier = Modifier.padding(vertical = 20.dp)
                Tab(
                    selected = selectedTabIndex == 0,
                    selectedContentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ),
                    unselectedContentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                    onClick = { selectedTabIndex = 0 },
                    modifier = Modifier.background(
                        if (selectedTabIndex == 0)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceContainerHigh
                    )

                ) {
                    Text(
                        text = "Join",
                        modifier = tabModifier,
                    )
                }
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    selectedContentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ),
                    unselectedContentColor = contentColorFor(
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ),
                    modifier = Modifier.background(
                        if (selectedTabIndex == 1)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Text(
                        modifier = tabModifier,
                        text = "Create",
                    )
                }

            }
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") }
            )
            if (selectedTabIndex == 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(8) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(randomColor().toColorInt()))
                                .size(24.dp)
                        )

                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(android.R.string.cancel))
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(android.R.string.ok))
                }
            }
        }
    }
}
