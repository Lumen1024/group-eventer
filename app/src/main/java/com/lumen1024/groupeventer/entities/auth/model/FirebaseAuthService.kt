package com.lumen1024.groupeventer.entities.auth.model

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    firebase: Firebase,
) : AuthService {

    private val auth = firebase.auth

    override val userId: String?
        get() = auth.currentUser?.uid


    override suspend fun login(email: String, password: String): Result<Unit> {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String): Result<Unit> {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            }.await()

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
        val listener : (FirebaseAuth) -> Unit = { callback() }
        auth.addAuthStateListener(listener)

        return Result.success { auth.removeAuthStateListener(listener) }
    }
}