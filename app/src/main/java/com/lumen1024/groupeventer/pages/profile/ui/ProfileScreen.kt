package com.lumen1024.groupeventer.pages.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.user.ui.Username
import com.lumen1024.groupeventer.pages.profile.model.ProfileViewModel
import com.lumen1024.groupeventer.shared.ui.Avatar
import com.lumen1024.groupeventer.shared.ui.SettingsToggleItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user by viewModel.userService.user.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.updateAvatar(it)
            }
        }
    )

    val handleEdit = { name: String ->
        viewModel.updateName(name)
    }
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            //.scrollable(scroll, Orientation.Vertical), // todo scroll?
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Avatar(
                modifier = Modifier
                    .clickable(role = Role.Button) { galleryLauncher.launch("image/*") },
                url = user?.avatarUrl
            )
            Username(
                username = user?.name ?: "",
                showEdit = true,
                onEdited = handleEdit
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        repeat(3) { // todo
            SettingsToggleItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                label = "Some settings 1",
                value = true
            ) {

            }
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Box(
            Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            TextButton(onClick = viewModel::logout) {
                Text("Logout this account")
            }
        }
    }
}