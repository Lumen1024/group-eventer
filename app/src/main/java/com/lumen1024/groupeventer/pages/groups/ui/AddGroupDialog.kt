package com.lumen1024.groupeventer.pages.groups.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.pages.groups.model.AddGroupViewModel
import com.lumen1024.groupeventer.shared.model.groupsColor

@Composable
fun AddGroupDialog(
    onDismiss: () -> Unit,
    viewModel: AddGroupViewModel = hiltViewModel()
//    onJoinGroup: (name: String, password: String) -> Unit,
//    onCreateGroup: (name: String, password: String, color: Color) -> Unit,
) {
    val dismiss by viewModel.dismiss.collectAsState()
    if (dismiss) onDismiss()

    val isLoading by viewModel.isLoading.collectAsState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isCreate by remember { mutableStateOf(false) }


        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // tabs
            GroupDialogTabRow(
                initialTab = 0,
                onChange = { index -> isCreate = (index == 1) }
            )

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

            // color picker
            if (isCreate) {
                var selected by remember { mutableIntStateOf(0) }
                GroupColorPicker(
                    colors = groupsColor,
                    selectedIndex = selected,
                    onSelect = { index -> selected = index }
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) CircularProgressIndicator()
                else {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(android.R.string.cancel))
                    }
                    TextButton(
                        onClick = {
                            if (isCreate)
                                viewModel.createGroup(name, password, Color.Red) // todo
                            else
                                viewModel.joinGroup(name, password)
                        }
                    ) {
                        Text(text = stringResource(android.R.string.ok))
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupDialogTabRow(
    modifier: Modifier = Modifier,
    initialTab: Int = 0,
    onChange: (Int) -> Unit,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
) {
    var selectedTabIndex by remember { mutableIntStateOf(initialTab) }

    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .size(width = 250.dp, height = 40.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        GroupDialogTab(
            name = "Join",
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex = 0 })
        GroupDialogTab(
            name = "Create",
            selected = selectedTabIndex == 1,
            onClick = { selectedTabIndex = 1 })
    }
}

@Composable
private fun GroupDialogTab(
    modifier: Modifier = Modifier,
    name: String,
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    onClick: () -> Unit,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier.background(
            if (selected)
                selectedColor
            else
                unselectedColor
        )
    ) {
        Text(
            text = name,
        )
    }
}

@Composable
fun GroupColorPicker(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        colors.forEachIndexed { index, color ->
            Box(
                modifier = Modifier
                    .apply {
                        if (index == selectedIndex)
                            border(width = 1.dp, MaterialTheme.colorScheme.outline)
                                .padding(4.dp)
                    }
                    .clip(CircleShape)
                    .background(color)
                    .size(24.dp)
                    .clickable { onSelect(index) }
            )
        }
    }
}
