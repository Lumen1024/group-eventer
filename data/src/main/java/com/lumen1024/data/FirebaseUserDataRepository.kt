package com.lumen1024.data

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.lumen1024.data.toUserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDataRepository @Inject constructor(
    firebase: Firebase,
) : com.lumen1024.domain.UserDataRepository {
    private val collection = firebase.firestore.collection("users")
    private val avatarsRef = firebase.storage.reference.child("avatars")

    override suspend fun add(userData: com.lumen1024.domain.UserData): Result<Unit> {
        return try {
            collection.document(userData.id).set(userData.toUserDataDto()).await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<com.lumen1024.domain.UserData> {
        return try {
            val userData = collection.document(id)
                .get()
                .await()
                .toObject(UserDataDto::class.java)?.toUserData()
                ?: throw Throwable("No user with this id")

            Result.success(userData)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun update(id: String, data: Map<String, Any>): Result<Unit> {
        return try {
            collection.document(id).update(data).await()
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatar(id: String, image: Uri): Result<Uri> {
        return try {
            val uploadTask = avatarsRef.child(id).putFile(image).await()
            val avatarUri = uploadTask.storage.downloadUrl.await()
            Result.success(avatarUri)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override fun listen(
        id: String,
        callback: (com.lumen1024.domain.UserData?) -> Unit,
    ): Result<() -> Unit> {
        return try {
            val unsubscribeFunc = collection.document(id).addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                val data = snapshot?.toObject(UserDataDto::class.java)?.toUserData()
                callback(data)
            }

            Result.success { unsubscribeFunc.remove() }
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}