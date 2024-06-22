package com.lumen1024.groupeventer.entities.auth.model

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebase: Firebase,
) : AuthService {

    override fun checkAuthorized(): Boolean {
        return firebase.auth.currentUser != null
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(AuthException.Unknown(""))
        }
    }

    override suspend fun register(
        email: String,
        name: String,
        password: String
    ): Result<Unit> {

        try {
            firebase.auth.createUserWithEmailAndPassword(email, password).await()
            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(AuthException.Unknown(""))
        }
    }

    override suspend fun logout() {
        firebase.auth.signOut()
    }
}