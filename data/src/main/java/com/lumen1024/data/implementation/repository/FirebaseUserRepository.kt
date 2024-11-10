package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.UserDto
import com.lumen1024.data.dto.toUser
import com.lumen1024.data.dto.toUserDto
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    firebase: Firebase,
) : UserRepository {
    private val collection = firebase.firestore.collection("users")

    override fun getUserById(id: String): Flow<User?> = callbackFlow {
        val query = collection.document(id)

        val registration = query.addSnapshotListener { snapshot, e ->
            if (e != null) return@addSnapshotListener
            val user = snapshot?.toObject(UserDto::class.java)?.toUser()
            trySend(user)
        }

        awaitClose { registration.remove() }
    }

    override suspend fun addUser(user: User): Result<Unit> {
        val task = collection.add(user.toUserDto())

        return tryCatchDerived("Failed add user") { task.await() }
    }

    override suspend fun updateUser(
        id: String,
        data: Map<String, Any>
    ): Result<Unit> {
        val task = collection.document(id).update(data)

        return tryCatchDerived("Failed update user") { task.await() }
    }
}