package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventStatus

@Composable
fun EventStatusIndicator(modifier: Modifier = Modifier, status: GroupEventStatus) {
    val color = when(status) {
        GroupEventStatus.Prepare -> colorResource(R.color.status_prepare)
        GroupEventStatus.Voting -> colorResource(R.color.status_voting)
        GroupEventStatus.Ended -> colorResource(R.color.status_ended)
    }
    Box(
        modifier = modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(color)
    )
}