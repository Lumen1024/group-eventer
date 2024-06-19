package com.lumen1024.groupeventer.entities.group_event.model

data class GroupEventResponse(
    val agreement: Boolean = false,
    val range: TimeRange = TimeRange(),
)