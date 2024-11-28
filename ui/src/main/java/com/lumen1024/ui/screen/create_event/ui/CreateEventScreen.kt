package com.lumen1024.ui.screen.create_event.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.ui.screen.create_event.model.CreateEventUiAction
import com.lumen1024.ui.screen.create_event.model.CreateEventUiState
import com.lumen1024.ui.screen.create_event.model.CreateEventViewModel
import com.lumen1024.ui.screen.create_event.model.TimePickerState
import com.lumen1024.ui.shared.tab.SimpleTabSwitch
import com.lumen1024.ui.shared.tab.TextSelect
import com.lumen1024.ui.shared.time.TimeRangeButton
import com.lumen1024.ui.shared.time.TimeRangePicker


@Composable
fun CreateEventScreen(
    state: CreateEventUiState,
    onAction: (CreateEventUiAction) -> Unit,
) {
    if (state is CreateEventUiState.Success) {
        Scaffold(
            modifier = Modifier
                .navigationBarsPadding()
                .statusBarsPadding(),
            topBar = {
                CreateEventTopBar(
                    title = state.name,
                    onBackClick = { onAction(CreateEventUiAction.OnBackClicked) },
                    onSaveClicked = { onAction(CreateEventUiAction.OnConfirmClicked) },
                )
            }
        ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Group Select
                val groupNames by remember { derivedStateOf { state.groups.map { it.name } } }
                val selectedGroupName by remember { derivedStateOf { state.groups[state.selectedGroup].name } }
                TextSelect(
                    selected = selectedGroupName,
                    options = groupNames,
                    onSelect = {
                        onAction(CreateEventUiAction.OnGroupSelected(
                            state.groups.indexOfFirst { it.name == selectedGroupName }
                        ))
                    },
                    shape = RoundedCornerShape(16.dp)
                )

                // Name and Description
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.name,
                        onValueChange = { onAction(CreateEventUiAction.OnNameChanged(it)) },
                        label = { Text(text = "Название") } // TODO: res
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(144.dp),
                        value = state.description,
                        onValueChange = { onAction(CreateEventUiAction.OnDescriptionChanged(it)) },
                        label = { Text(text = "Описание") } // TODO: res
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Duration
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Duration")
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = state.durationHeader)
                        Icon(imageVector = Icons.Default.AvTimer, contentDescription = "")
                    }
                }
                Slider(
                    value = state.durationSliderValue,
                    onValueChange = { onAction(CreateEventUiAction.OnDurationSliderValueChanged(it)) },
                    valueRange = 1f..6f,
                    steps = 4,
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Status Tabs
                val statusesNames by remember { derivedStateOf { state.statuses.map { it.name } } }
                SimpleTabSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
                    tabHeight = 44.dp,
                    tabs = statusesNames,
                    unselectedColor = MaterialTheme.colorScheme.background,
                    onChange = {
                        onAction(CreateEventUiAction.OnStatusSelected(it))
                    }
                )

                // Time
                when (state.timePickerState) {
                    is TimePickerState.Single -> TimeRangeButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        date = state.timePickerState.time,
                        onChanged = { onAction(CreateEventUiAction.OnTimeChanged(it)) }
                    )

                    is TimePickerState.Range -> {
                        TimeRangePicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            value = state.timePickerState.timeRange,
                            onChange = { onAction(CreateEventUiAction.OnTimeRangeChanged(it)) }
                        )
                    }
                }
            }
        }
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val actions = viewModel::onAction
    CreateEventScreen(state, actions)
}

