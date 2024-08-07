package com.lumen1024.groupeventer.pages.create_event.ui

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.event.model.GroupEventStatus
import com.lumen1024.groupeventer.pages.create_event.model.CreateEventViewModel
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.model.TimeRange
import com.lumen1024.groupeventer.shared.ui.Select
import com.lumen1024.groupeventer.shared.ui.SimpleTabSwitch
import com.lumen1024.groupeventer.shared.ui.TimeRangePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
) {
//    val isNewEvent by remember { derivedStateOf { true } }

//    val availableStatuses by remember { derivedStateOf { if (isNewEvent) listOf(GroupEventStatus.Prepare) else emptyList() } }

    val statuses = GroupEventStatus.entries.map { it.name }

    var status by remember { mutableStateOf(GroupEventStatus.Prepare) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val requestedRanges = remember { mutableStateListOf<TimeRange>() }

    val groups by viewModel.userStateHolder.groups.collectAsState()

    val groupNames by remember { derivedStateOf { groups.map { it.name } } }

    var selectedGroupName by remember(groupNames) { mutableStateOf(groupNames.getOrElse(0) { "" }) }

    Scaffold(modifier = Modifier
        .navigationBarsPadding()
        .statusBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { viewModel.navigator.tryNavigateTo(Screen.Events) }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
                Text(
                    text = name.ifEmpty { "New Event" },
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = {
                        val group =
                            groups.find { group -> group.name == selectedGroupName }
                                ?: return@TextButton

                        viewModel.saveEvent(
                            group,
                            name,
                            description,
                            status,
                            requestedRanges
                        )
                    }) {
                    Text(text = "save")
                }
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedVisibility(visible = groupNames.isNotEmpty()) {
                    Select(
                        selected = selectedGroupName,
                        options = groupNames,
                        onSelect = { selectedGroupName = it },
                        fill = true,
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Название") } // TODO: res
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(text = "Описание") } // TODO: res
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                SimpleTabSwitch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
                    tabHeight = 44.dp,
                    tabs = statuses,
                    unselectedColor = MaterialTheme.colorScheme.background,
                    onChange = {
                        status = GroupEventStatus.valueOf(statuses[it])
                    }
                )


                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Time ranges")
                        IconButton(onClick = { requestedRanges.add(TimeRange()) }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add new time range"
                            )
                        }
                    }

                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(count = requestedRanges.size) { index ->
//                            val dismissState = rememberSwipeToDismissBoxState(
//                                confirmValueChange = {
//                                    when (it) {
//                                        SwipeToDismissBoxValue.StartToEnd -> return@rememberSwipeToDismissBoxState false
//
//                                        SwipeToDismissBoxValue.EndToStart -> {
//                                            requestedRanges.remove(requestedRanges[index])
//                                        }
//
//                                        SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
//                                    }
//                                    return@rememberSwipeToDismissBoxState true
//                                },
//                                // positional threshold of 25%
//                                positionalThreshold = { it * .25f }
//                            )
//                            SwipeToDismissBox(state = dismissState, backgroundContent = {}) {
                            TimeRangePicker(
                                value = requestedRanges[index],
                                onChange = { value -> requestedRanges[index] = value })
//                            }
                        }
                    }
                }
            }
        }
    }

}