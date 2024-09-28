package com.lumen1024.groupeventer.entities.auth.model

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import com.lumen1024.domain.UserData
import com.lumen1024.domain.UserDataRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    firebase: Firebase,
    private val userDataRepository: com.lumen1024.domain.UserDataRepository,
) : com.lumen1024.domain.AuthService {
    private val auth = firebase.auth

    override val userId: String?
        get() = auth.currentUser?.uid

    override suspend fun refreshToken(): Result<Unit> {
        return try {
            auth.currentUser?.reload()?.await()
            Result.success(Unit)
        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()

            val user = authResult.user ?: throw AuthException.Unknown("User is null")
            if (userDataRepository.get(user.uid).isFailure) {
                initUserData(user.uid, user.displayName ?: "User", user.photoUrl)
            }

            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(e)
        }
    }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            initUserData(authResult.user?.uid ?: throw AuthException.Unknown("User is null"), name)

            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        auth.signOut()
        return Result.success(Unit)
    }

    override fun listen(callback: () -> Unit): Result<() -> Unit> {
        val listener: (FirebaseAuth) -> Unit = { callback() }
        auth.addAuthStateListener(listener)

        return Result.success { auth.removeAuthStateListener(listener) }
    }

    private suspend fun initUserData(userId: String, name: String, avatarUrl: Uri? = null) {
        val userData = com.lumen1024.domain.UserData(
            id = userId,
            name = name,
            avatarUrl = avatarUrl
        )
        userDataRepository.add(userData).onFailure { throw it }
    }
}