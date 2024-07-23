package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.user_data.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserStateHolder {
    val groups: StateFlow<List<Group>>
    val userData: StateFlow<UserData?>
}