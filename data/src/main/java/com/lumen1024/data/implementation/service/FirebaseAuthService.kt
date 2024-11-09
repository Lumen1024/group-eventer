package com.lumen1024.data.implementation.service

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    firebase: Firebase,
    private val userRepository: UserRepository,
) : AuthService {
    private val auth = firebase.auth

    override val userId: String?
        get() = auth.currentUser?.uid

    override suspend fun login(email: String, password: String): Result<Unit> {
        return tryCatchDerived("Failed login") {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("User is null")
            if (userRepository.getUserById(user.uid).isFailure) {
                initUserData(user.uid, user.displayName)
            }
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        return tryCatchDerived("Failed register") {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("User is null")
            initUserData(user.uid, name)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return tryCatchDerived("Failed logout") { auth.signOut() }
    }

    override fun listen(callback: () -> Unit): Result<() -> Unit> {
        val listener: (FirebaseAuth) -> Unit = { callback() }

        return tryCatchDerived("Failed listen auth state") {
            auth.addAuthStateListener(listener)
            return@tryCatchDerived { auth.removeAuthStateListener(listener) }
        }
    }

    private suspend fun initUserData(userId: String, name: String?) {
        var user = User(id = userId)
        if (name != null) user = user.copy(name = name)

        tryCatchDerived("Failed init user data") {
            userRepository.addUser(user).onFailure { throw it }
        }
    }
}