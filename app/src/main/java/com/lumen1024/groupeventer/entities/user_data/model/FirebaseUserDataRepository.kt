package com.lumen1024.groupeventer.entities.user_data.model

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.lumen1024.groupeventer.shared.model.RepositoryException
import com.lumen1024.groupeventer.shared.model.toRepositoryException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDataRepository @Inject constructor(
    firebase: Firebase,
) : UserDataRepository {
    private val collection = firebase.firestore.collection("users")

    override suspend fun get(userId: String): Result<UserData> {
        try {
            val userData = collection
                .document(userId)
                .get()
                .await()
                .toObject(UserData::class.java)

            if (userData == null) {
                // todo: remove? <-> @Answer: lumen1024 -> what is going on?

                val newUserData = UserData()

                collection
                    .document(userId)
                    .set(newUserData)
                    .await()

                return Result.success(newUserData)

//                return Result.failure(
//                    UserRepositoryException(
//                        code = RepositoryException.Code.UNKNOWN,
//                        "User data is null"
//                    )
//                )
            }

            return Result.success(userData)
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                UserRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override suspend fun updateUserData(userId: String, data: Map<String, Any>): Result<Void> {
        return try {
            Result.success(
                collection
                    .document(userId)
                    .update(data)
                    .await()
            )
        } catch (e: Exception) {
            Log.e("repository", e.message, e)
            if (e is FirebaseFirestoreException) {
                return Result.failure(e.toRepositoryException())
            }

            return Result.failure(
                UserRepositoryException(
                    RepositoryException.Code.UNKNOWN,
                    e.message
                )
            )
        }
    }

    override fun listen(
        userId: String,
        callback: (UserData?) -> Unit,
    ): () -> Unit {
        val registration = collection.document(userId).addSnapshotListener { snapshot, e ->
            if (e !== null) {
                return@addSnapshotListener
            }

            callback(snapshot!!.toObject())
        }

        return { registration.remove() }
    }
}