package com.lumen1024.domain.data

data class UserData(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val groups: List<String> = emptyList(),
)

