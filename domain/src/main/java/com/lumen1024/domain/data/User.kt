package com.lumen1024.domain.data

data class User(
    val id: String,
    val name: String = "New user",
    val avatarUrl: String? = null,
    val groups: List<String> = emptyList(),
)

