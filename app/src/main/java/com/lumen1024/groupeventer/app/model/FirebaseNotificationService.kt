package com.lumen1024.groupeventer.app.model

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserActions
import javax.inject.Inject


class FirebaseNotificationService @Inject constructor(
    private val userActions: FirebaseUserActions,
    private val notificationService: SimpleNotificationService,
) : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title ?: return
            val body = remoteMessage.notification?.body ?: return

            notificationService.sendNotification(title, body)
        }
    }

    // TODO: need to override
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Token", token)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply()
    }

    companion object {
        fun getToken(context: Context): String? {
            return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
        }
    }
}