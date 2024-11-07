package com.lumen1024.domain.usecase

import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.User
import kotlinx.coroutines.flow.StateFlow

interface UserStateHolder {
    val groups: StateFlow<List<Group>>
    val user: StateFlow<User?>
}