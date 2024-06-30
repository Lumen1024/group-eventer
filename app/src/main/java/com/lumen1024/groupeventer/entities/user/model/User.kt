package com.lumen1024.groupeventer.entities.user.model

data class UserGroup(
    val isMuted: Boolean = false,
)

data class UserData(
    val id: String = "",
    val groups: Map<String, Boolean> = emptyMap(),
)