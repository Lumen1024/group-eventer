package com.lumen1024.groupeventer.entities.user.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView

interface CropImageColors {
    val background: Color
    val topBar: Color
    val onTopBar: Color
}

fun getCropperOptions(cropImageColors: CropImageColors): CropImageContractOptions {
    return CropImageContractOptions(
        null,
        CropImageOptions(
            imageSourceIncludeCamera = false,
            cropShape = CropImageView.CropShape.OVAL,
            fixAspectRatio = true,
            aspectRatioX = 1,
            aspectRatioY = 1,
            guidelines = CropImageView.Guidelines.OFF,
            borderLineThickness = 0f,

            activityBackgroundColor = cropImageColors.background.toArgb(),
            toolbarColor = cropImageColors.background.toArgb(),
            toolbarBackButtonColor = cropImageColors.onTopBar.toArgb(),
            activityMenuIconColor = cropImageColors.onTopBar.toArgb(),
            activityMenuTextColor = cropImageColors.onTopBar.toArgb(),
        )
    )
}