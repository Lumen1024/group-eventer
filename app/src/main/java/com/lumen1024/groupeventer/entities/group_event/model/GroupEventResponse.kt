package com.lumen1024.groupeventer.entities.group_event.model

import com.lumen1024.groupeventer.shared.model.TimeRange

data class GroupEventResponse(
    val agreement: Boolean = false,
    val range: TimeRange = TimeRange(),
)