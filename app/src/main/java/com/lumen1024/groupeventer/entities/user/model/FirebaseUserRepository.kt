package com.lumen1024.groupeventer.entities.user.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import com.lumen1024.groupeventer.shared.model.RepositoryException
import com.lumen1024.groupeventer.shared.model.toRepositoryException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    private val firebase: Firebase,
) : UserRepository {
    override suspend fun getData(userId: String): Result<UserData> {
        try {
            val userData = firebase.firestore
                .collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(UserData::class.java)

            if (userData === null) {
                // TODO remove?

                val newUserData = UserData(id = userId)

                firebase.firestore
                    .collection("users")
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

    override suspend fun updateData(userId: String, data: Map<String, Any>): Result<Void> {
        return try {
            Result.success(
                firebase.firestore
                    .collection("users")
                    .document(userId)
                    .update(data)
                    .await()
            )
        } catch (e: Exception) {
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
}