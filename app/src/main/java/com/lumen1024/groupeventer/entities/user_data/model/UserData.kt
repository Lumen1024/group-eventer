package com.lumen1024.groupeventer.entities.user_data.model

import android.net.Uri

data class UserData(
    val id: String = "",
    val name: String = "",
    val avatarUrl: Uri? = null,
    val groups: List<String> = emptyList()
)

