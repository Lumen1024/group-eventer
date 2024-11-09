package com.lumen1024.data.implementation.service

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.service.AvatarService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAvatarService @Inject constructor(
    firebase: Firebase
) : AvatarService {
    val storage = firebase.storage.reference.child("avatars")

    override suspend fun getAvatar(userId: String): Result<String?> {
        val task = storage.child(userId).downloadUrl

        return tryCatchDerived("Failed get avatar") {
            val url = task.await().toString()
            return@tryCatchDerived url
        }
    }

    override suspend fun updateAvatar(
        userId: String,
        imageURI: String
    ): Result<Unit> {
        val task = storage.child(userId).putFile(Uri.parse(imageURI))

        return tryCatchDerived("Failed update avatar") {task.await() }
    }
}