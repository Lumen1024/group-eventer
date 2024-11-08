package com.lumen1024.data.dto

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.data.GroupRole

data class GroupDto(
    val id: String = "",

    val name: String = "",
    val color: GroupColor = GroupColor.RED,
    val password: String = "",

    val members: Map<String, GroupRole> = emptyMap(),
)

fun Group.toGroupDto() = GroupDto(
    id = id,

    name = name,
    color = color,
    password = password,

    members = members,
)

fun GroupDto.toGroup() = Group(
    id = id,

    name = name,
    color = color,
    password = password,

    members = members,
)

