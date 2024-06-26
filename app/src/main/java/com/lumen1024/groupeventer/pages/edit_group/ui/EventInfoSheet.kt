package com.lumen1024.groupeventer.pages.edit_group.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.pages.edit_group.model.EventInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventSheet(
    viewmodel: EventInfoViewModel = hiltViewModel(),
) {
    ModalBottomSheet(onDismissRequest = {  }) {

        Text(text = "")
        Text(text = "")
    }
}