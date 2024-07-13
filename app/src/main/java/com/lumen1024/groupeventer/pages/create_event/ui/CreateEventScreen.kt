package com.lumen1024.groupeventer.pages.create_event.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventStatus
import com.lumen1024.groupeventer.pages.create_event.model.CreateEventViewModel
import com.lumen1024.groupeventer.shared.ui.EventStatusIndicator
import com.lumen1024.groupeventer.shared.ui.EventStatusSelect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel = hiltViewModel(),
) {
    var status by remember { mutableStateOf(GroupEventStatus.Prepare) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var dropdownExpanded by remember { mutableStateOf(false) }
    val dropdownItems = listOf(
        GroupEventStatus.Prepare,
        GroupEventStatus.Voting,
        GroupEventStatus.Ended,
    )
    
    Scaffold(modifier = Modifier
        .navigationBarsPadding()
        .statusBarsPadding(),

        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                }
                Text(text = name.ifEmpty { "New Event" }, fontSize = 20.sp)
                TextButton(modifier = Modifier.padding(horizontal = 8.dp),
                    onClick = { /*TODO*/ }) {
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = it }) {
                Text(text = "")
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    dropdownItems.forEach {
                        EventStatusIndicator(status = it)
                        Text(text = it.name)
                    }
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Название") }
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(text = "Описание") }
            )
            EventStatusSelect(modifier = Modifier.padding(20.dp)) {

            }
        }
    }

}