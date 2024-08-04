package com.lumen1024.groupeventer.pages.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.canhub.cropper.CropImageContract
import com.lumen1024.groupeventer.entities.user.ui.Username
import com.lumen1024.groupeventer.pages.profile.model.ProfileViewModel
import com.lumen1024.groupeventer.shared.config.CropImageColors
import com.lumen1024.groupeventer.shared.config.getCropperOptions
import com.lumen1024.groupeventer.shared.ui.Avatar
import com.lumen1024.groupeventer.shared.ui.SettingsToggleItem

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val userData by viewModel.userStateHolder.userData.collectAsState()

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            result.uriContent?.let {
                viewModel.updateAvatar(it)
            }
        }

    val handleEdit = { name: String ->
        viewModel.updateName(name)
    }

    val cropImageColors = object : CropImageColors {
        override val background = MaterialTheme.colorScheme.background
        override val topBar = MaterialTheme.colorScheme.surfaceContainer
        override val onTopBar = contentColorFor(backgroundColor = background)
    }

    val openCropper = {
        galleryLauncher.launch(getCropperOptions(cropImageColors))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // TODO: scroll?
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Avatar(
                showBorder = true,
                modifier = Modifier
                    .clickable(role = Role.Button, onClick = openCropper),
                url = userData?.avatarUrl
            )
            Username(
                username = userData?.name ?: "",
                showEdit = true,
                onEdited = handleEdit
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        repeat(3) { // TODO: profile settings content
            SettingsToggleItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                label = "Some settings 1",
                value = true
            ) { }
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