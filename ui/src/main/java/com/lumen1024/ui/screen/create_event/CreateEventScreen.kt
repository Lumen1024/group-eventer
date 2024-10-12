package com.lumen1024.ui.screen.create_event

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.ui.shared.SimpleTabSwitch
import com.lumen1024.ui.shared.TextSelect
import com.lumen1024.ui.shared.time.TimeRangeButton
import com.lumen1024.ui.shared.time.TimeRangePicker
import java.time.Duration
import java.time.Instant

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
) {
    val groups by viewModel.userStateHolder.groups.collectAsState()
    val groupNames by remember { derivedStateOf { groups.map { it.name } } }

    var status by remember { mutableStateOf(GroupEventStatus.Voting) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableFloatStateOf(1f) }

    val statuses = GroupEventStatus.entries.map { it.name }

    var initialRange: TimeRange by remember { mutableStateOf(TimeRange()) }
    var startTime: Instant by remember { mutableStateOf(Instant.now()) }


    var selectedGroupName by remember(groupNames) { mutableStateOf(groupNames.getOrElse(0) { "" }) }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
            CreateEventTopBar(
                title = name,
                onBackClick = { viewModel.navigator.back() },
                onSafeClicked = {
                    val group = groups.find { group -> group.name == selectedGroupName }
                        ?: return@CreateEventTopBar

                    when (status) {
                        GroupEventStatus.Voting -> viewModel.saveEvent(
                            event = Event(
                                status = status,
                                name = name,
                                description = description,
                                duration = Duration.ofHours(duration.toLong()),
                                initialRange = initialRange
                            ),
                            group = group,
                        )

                        GroupEventStatus.Scheduled,
                        GroupEventStatus.Finish -> viewModel.saveEvent(
                            event = Event(
                                status = status,
                                name = name,
                                description = description,
                                duration = Duration.ofHours(duration.toLong()),
                                startTime = startTime,
                            ),
                            group = group
                        )
                    }

                },
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
            AnimatedVisibility(visible = groupNames.isNotEmpty()) {
                TextSelect(
                    selected = selectedGroupName,
                    options = groupNames,
                    onSelect = { selectedGroupName = it },
                    shape = RoundedCornerShape(16.dp)
                )
            }

            // Name and Description
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp),
                    value = description,
                    onValueChange = { description = it },
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
                    Text(text = "${duration.toInt()}h")
                    Icon(imageVector = Icons.Default.AvTimer, contentDescription = "")
                }
            }
            Slider(
                value = duration,
                onValueChange = { duration = it },
                valueRange = 1f..6f,
                steps = 4,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Status Tabs
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

            // Time
            when (status) {
                GroupEventStatus.Voting -> TimeRangePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    value = initialRange,
                    onChange = { initialRange = it }
                )

                GroupEventStatus.Scheduled,
                GroupEventStatus.Finish -> TimeRangeButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    date = startTime,
                    onChanged = { startTime = it }
                )

            }
        }
    }

}

