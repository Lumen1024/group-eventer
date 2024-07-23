package com.lumen1024.groupeventer.entities.user_data.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.entities.group.model.UserDataDto
import com.lumen1024.groupeventer.entities.group.model.toUserData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDataRepository @Inject constructor(
    firebase: Firebase,
) : UserDataRepository {
    private val collection = firebase.firestore.collection("users")

    override suspend fun add(userData: UserData): Result<Unit> {
        return try {
            collection.document(userData.id).set(userData).await()

            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun get(id: String): Result<UserData> {
        return try {
            val userData = collection.document(id)
                .get().await()
                .toObject(UserData::class.java)
                ?: throw Throwable("No user with this id")

            Result.success(userData)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun update(id: String, data: Map<String, Any>): Result<Unit> {
        return try {
            collection.document(id).update(data)
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override fun listen(
        id: String,
        callback: (UserData?) -> Unit,
    ): Result<Unit> {
        return try {
            collection.document(id).addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener

                val data = snapshot?.toObject(UserDataDto::class.java)?.toUserData()
                callback(data)
            }
            Result.success(Unit)

        } catch (e : Throwable) {
            Result.failure(e)
        }
    }
}