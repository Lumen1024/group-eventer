package com.lumen1024.presentation.widgets.add_group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group.ui.GroupColorPicker
import com.lumen1024.groupeventer.shared.ui.NameTextField
import com.lumen1024.groupeventer.shared.ui.PasswordTextField
import com.lumen1024.groupeventer.shared.ui.SimpleTabSwitch
import com.lumen1024.presentation.widgets.add_group.model.AddGroupViewModel

@Composable
fun AddGroupDialog(
    onDismiss: () -> Unit,
    viewModel: AddGroupViewModel = hiltViewModel(),
) {
    val dismiss by viewModel.dismiss.collectAsState()
    LaunchedEffect(dismiss) {
        if (dismiss) {
            onDismiss()
            viewModel.resetDismiss()
        }
    }

    val isLoading by viewModel.isLoading.collectAsState()

    var selectedColor by remember { mutableStateOf(com.lumen1024.domain.GroupColor.entries[0]) }

    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isCreate by remember { mutableStateOf(false) }

    val onConfirm by remember {
        derivedStateOf {
            // TODO: refactor
            if (isCreate)
                return@derivedStateOf { viewModel.createGroup(name, password, selectedColor) }
            else
                return@derivedStateOf { viewModel.joinGroup(name, password) }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SimpleTabSwitch(
                    initialTab = 0,
                    tabs = listOf("Join", "Create"),
                    onChange = { index -> isCreate = (index == 1) }
                )

                NameTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onChange = { name = it },
                )
                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onChange = { password = it },
                )

                if (isCreate) {
                    GroupColorPicker(
                        colors = com.lumen1024.domain.GroupColor.entries,
                        selectedColor = selectedColor,
                        onSelect = { selectedColor = it },
                        circleSize = 24.dp
                    )
                }
            }

            DialogButtons(
                modifier = Modifier.padding(horizontal = 16.dp),
                isLoading = isLoading,
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }
}

// TODO: refactor picker dialog? New base dialog?
@Composable
fun DialogButtons(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        if (isLoading) CircularProgressIndicator()
        else {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(android.R.string.cancel))
            }
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        }
    }
}
