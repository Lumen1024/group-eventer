package com.lumen1024.groupeventer.pages.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
        Button(onClick = { viewModel.logout() }) {
            Text("Logout")
        }
    }
}