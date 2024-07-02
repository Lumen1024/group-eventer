package com.lumen1024.groupeventer.pages.events.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group_event.ui.GroupEventCard
import com.lumen1024.groupeventer.pages.events.model.EventsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EventsScreen(
    viewModel: EventsViewModel = hiltViewModel(),
) {
    val events = viewModel.events.collectAsState()

    var isSheetOpen by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //isSheetOpen = true
                viewModel.addEvent()
            }) {
                Icon(Icons.Default.Add, "")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events.value, key = { event -> event.id })
            {
                GroupEventCard(
                    modifier = Modifier.clickable {

                    },
                    event = it
                )
            }
        }

        if (isSheetOpen)
            ModalBottomSheet(onDismissRequest = { isSheetOpen = false }) {
                repeat(10) {
                    Text(text = "10erergefwefrqgeqrg")
                }
            }
    }
}

