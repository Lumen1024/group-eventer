package com.lumen1024.data.implementation.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.lumen1024.data.dto.UserDto
import com.lumen1024.data.dto.toUser
import com.lumen1024.data.dto.toUserDto
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    firebase: Firebase,
) : UserRepository {
    private val collection = firebase.firestore.collection("users")

    override suspend fun getUserById(id: String): Result<User> {
        val query = collection.document(id).get()

        return tryCatchDerived("Failed get user by id") {
            val user = query.await().toObject(UserDto::class.java)?.toUser()
                ?: throw Exception("User not found")
            return@tryCatchDerived user
        }
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

    override fun listenChanges(
        id: String,
        callback: (User) -> Unit
    ): Result<() -> Unit> {
        val query = collection.document(id)

        return tryCatchDerived<() -> Unit>("Failed listen changes") {
            val registration = query.addSnapshotListener { snapshot, e ->
                if (e != null) return@addSnapshotListener
                val data = snapshot?.toObject(UserDto::class.java)?.toUser()
                if (data != null) callback(data)
            }
            return@tryCatchDerived { registration.remove() }
        }
    }
}