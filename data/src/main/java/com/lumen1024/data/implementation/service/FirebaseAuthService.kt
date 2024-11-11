package com.lumen1024.data.implementation.service

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.lumen1024.data.tryCatchDerived
import com.lumen1024.domain.data.User
import com.lumen1024.domain.repository.UserRepository
import com.lumen1024.domain.service.AuthService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    firebase: Firebase,
    private val userRepository: UserRepository,
) : AuthService {
    private val auth = firebase.auth

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCurrentUser(): Flow<User?> = flow {
        getCurrentFirebaseUser().flatMapLatest { firebaseUser ->
            if (firebaseUser == null) return@flatMapLatest flowOf(null)

            var user = userRepository.getUserById(firebaseUser.uid).first()
            if (user == null) {
                initUserData(firebaseUser.uid, firebaseUser.displayName)
            }

            user = userRepository.getUserById(firebaseUser.uid).first()
            return@flatMapLatest flowOf(user)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return tryCatchDerived("Failed login") {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user ?: throw Exception("User is null")
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        return tryCatchDerived("Failed register") {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user ?: throw Exception("User is null")
        }
    }

    override suspend fun logout(): Result<Unit> {
        return tryCatchDerived("Failed logout") { auth.signOut() }
    }

    private suspend fun initUserData(userId: String, name: String?) {
        var user = User(id = userId)
        if (name != null) user = user.copy(name = name)

        tryCatchDerived("Failed init user data") {
            userRepository.addUser(user).onFailure { throw it }
        }
    }

    private fun getCurrentFirebaseUser(): Flow<FirebaseUser?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val userId = auth.currentUser
            trySend(userId)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose { auth.removeAuthStateListener(authStateListener) }
    }
}