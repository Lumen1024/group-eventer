package com.lumen1024.groupeventer.entities.user.model

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

data class UserGroupProperties(
    val isMuted: Boolean,
)

data class UserData(
    val id: String = "",
    val groups: List<String> = emptyList(),
    val groupProperties: Map<String, UserGroupProperties> = emptyMap(),
) {
    fun groupIsMuted(groupId: String): Boolean {
        return groupProperties[groupId]?.isMuted ?: false
    }
}

data class User(
    val id: String,
    val avatarUrl: Uri?,
    val name: String?,
)

fun FirebaseUser.toUser(): User {
    return User(this.uid, this.photoUrl, this.displayName)
}