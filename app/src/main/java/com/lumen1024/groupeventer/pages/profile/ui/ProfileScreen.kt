package com.lumen1024.groupeventer.pages.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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

    val cropImageColors = object : CropImageColors {
        override val background = MaterialTheme.colorScheme.background
        override val topBar = MaterialTheme.colorScheme.surfaceContainer
        override val onTopBar = contentColorFor(backgroundColor = background)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
                    .size(232.dp)
                    .clickable(
                        role = Role.Button,
                        onClick = { galleryLauncher.launch(getCropperOptions(cropImageColors)) }),
                url = userData?.avatarUrl
            )
            Username(
                username = userData?.name ?: "",
                showEdit = true,
                onEdited = { viewModel.updateName(it) }
            )
        }
    }
}