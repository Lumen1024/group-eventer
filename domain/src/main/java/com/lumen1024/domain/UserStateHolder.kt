package com.lumen1024.domain

import kotlinx.coroutines.flow.StateFlow

interface UserStateHolder {
    val groups: StateFlow<List<Group>>
    val userData: StateFlow<UserData?>
}