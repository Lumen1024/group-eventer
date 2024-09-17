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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.lumen1024.groupeventer.entities.event.model.GroupEventStatus
import com.lumen1024.groupeventer.pages.create_event.model.CreateEventViewModel
import com.lumen1024.groupeventer.shared.model.TimeRange
import com.lumen1024.groupeventer.shared.ui.Select
import com.lumen1024.groupeventer.shared.ui.SimpleTabSwitch
import com.lumen1024.groupeventer.shared.ui.TimeRangeButton
import com.lumen1024.groupeventer.shared.ui.TimeRangePicker
import java.time.Instant

@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
) {
    val statuses = GroupEventStatus.entries.map { it.name }

    var status by remember { mutableStateOf(GroupEventStatus.Prepare) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableFloatStateOf(1f) }

    val groups by viewModel.userStateHolder.groups.collectAsState()
    val groupNames by remember { derivedStateOf { groups.map { it.name } } }

    var selectedGroupName by remember(groupNames) { mutableStateOf(groupNames.getOrElse(0) { "" }) }

    Scaffold(modifier = Modifier
        .navigationBarsPadding()
        .statusBarsPadding(),
        topBar = {
            CreateEventTopBar(
                title = name,
                onBackClick = { viewModel.navigator.back() },
                onSafeClicked = {
                    val group = groups.find { group -> group.name == selectedGroupName }
                        ?: return@CreateEventTopBar
                    viewModel.saveEvent(
                        group,
                        name,
                        description,
                        status,
                    )
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

            when (status) {
                GroupEventStatus.Prepare -> {
                    TimeRangePicker(value = TimeRange()) {

                    }
                }

                GroupEventStatus.Voting,
                GroupEventStatus.Finish -> {
                    TimeRangeButton(
                        modifier = Modifier.fillMaxWidth(),
                        date = Instant.now(),
                        onChanged = {}
                    )
                }
            }
        }
    }

}

@Composable
fun CreateEventTopBar(
    title: String,
    onBackClick: () -> Unit,
    onSafeClicked: () -> Unit,
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
            onClick = { onSafeClicked() }
        ) {
            Text(text = "save")
        }
    }
}