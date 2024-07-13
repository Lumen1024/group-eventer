package com.lumen1024.groupeventer.entities.auth.model

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.lumen1024.groupeventer.entities.user.model.UserService
import com.lumen1024.groupeventer.entities.user.model.User
import com.lumen1024.groupeventer.entities.user.model.toUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebase: Firebase,
) : AuthService {

    private val listeners = mutableSetOf<(FirebaseAuth) -> Unit>()

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

            this.updateUserAndTriggerListeners()

            return Result.success(Unit)
        } catch (e: Exception) {
            Log.e("service", e.message, e)
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
            Log.e("service", e.message, e)
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

            this.updateUserAndTriggerListeners()

            return Result.success(Unit)
        } catch (e: Exception) {
            Log.e("service", e.message, e)
            return if (e is FirebaseAuthException)
                Result.failure(e.toAuthException())
            else
                Result.failure(AuthException.Unknown(""))
        }
    }

    override suspend fun logout() {
        firebase.auth.signOut()
    }

    override fun listenChanges(callback: (User?) -> Unit): () -> Unit {
        val listener: (auth: FirebaseAuth) -> Unit = {
            callback(it.currentUser?.toUser())
        }

        Firebase.auth.addAuthStateListener(listener)
        listeners.add(listener)

        listener(Firebase.auth)

        return {
            Firebase.auth.removeAuthStateListener(listener)
            listeners.remove(listener)
        }
    }

    private suspend fun updateUserAndTriggerListeners() {
        Firebase.auth.currentUser?.reload()?.await()

        for (listener in listeners) {
            listener(Firebase.auth)
        }
    }
}