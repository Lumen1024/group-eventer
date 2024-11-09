package com.lumen1024.data.dto

import com.lumen1024.domain.data.User

data class UserDto(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String? = null,
    val groups: List<String> = emptyList(),
)

fun User.toUserDto() = UserDto(
    id = id,
    name = name,
    groups = groups,
)

fun UserDto.toUser() = User(
    id = id,
    name = name,
    groups = groups,
)