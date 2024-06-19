package com.lumen1024.groupeventer.shared.lib

import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseException

suspend fun <T> handleExceptionWithToast(context: Context, action: suspend () -> T): T? {
    try {
        return action()
    } catch (e: Exception) {
        if (e is FirebaseException) {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    return null
}