package com.lumen1024.groupeventer.entities.auth.model

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebase: Firebase,
) : AuthService {

    override fun checkAuthorized(): Boolean {
        return firebase.auth.currentUser != null
    }

    override suspend fun updateUser(name: String?, avatarUrl: Uri?): Result<Unit> {
        try {
            val profileUpdates = UserProfileChangeRequest.Builder()

            if (name !== null) {
                profileUpdates.displayName = name
            }

            if (avatarUrl !== null) {
                profileUpdates.photoUri = avatarUrl
            }

            Firebase.auth.currentUser?.updateProfile(profileUpdates.build())?.await()
            Firebase.auth.currentUser?.reload()?.await()

            return Result.success(Unit)
        } catch (e: Exception) {
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(AuthException.Unknown(""))
        }
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
        password: String,
    ): Result<Unit> {

        try {
            firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                }

                it.result.user?.updateProfile(profileUpdates)
            }.await()

            Firebase.auth.currentUser?.reload()

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