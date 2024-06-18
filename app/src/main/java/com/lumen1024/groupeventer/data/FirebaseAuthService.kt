package com.lumen1024.groupeventer.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebase: Firebase,
) : AuthService {

    override fun checkAuthorized(): Boolean {
        return firebase.auth.currentUser != null
    }

    override suspend fun login(email: String, password: String): FirebaseUser? {
        firebase.auth.signOut()
        return firebase.auth.signInWithEmailAndPassword(email, password).await().user
    }

    override suspend fun register(email: String, name: String, password: String): FirebaseUser? {
        firebase.auth.signOut()
        return firebase.auth.createUserWithEmailAndPassword(email, password).await().user
    }

    override suspend fun logout() {
        firebase.auth.signOut()
    }
}