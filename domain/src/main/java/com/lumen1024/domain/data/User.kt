package com.lumen1024.domain.data

data class User(
    val id: String,
    val name: String = "New user",
    val groups: List<String> = emptyList(),
)

