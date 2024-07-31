package com.lumen1024.groupeventer.app.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserActions
import javax.inject.Inject


class FirebaseNotificationService @Inject constructor(
    val userActions: FirebaseUserActions,
) : FirebaseMessagingService() {
    companion object {
        fun getToken(context: Context): String? {
            return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty")
        }
    }

    // TODO: check needed dispatcher
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body

            if (title === null || body === null) {
                return
            }

            // Customize notification handling as per your requirements
            sendNotification(title, body)
        }
    }

    private fun sendNotification(title: String, body: String) {
        val channelId = "data_change_channel"
        Log.d("Message", "$title $body")

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Data Change Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

// TODO: Example
//    private fun sendNotification(title: String?, body: String?) {
//        // Create an intent to open the desired activity when the notification is tapped
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        // Build the notification
//        val notificationBuilder: NotificationCompat.Builder =
//            NotificationCompat.Builder(this, "channel_id")
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(R.drawable.ic_notification)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//
//        // Display the notification
//        val notificationManager =
//            getSystemService<Any>(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.notify(0, notificationBuilder.build())
//    }


    // TODO: need to override
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Token", token)
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", token).apply()
    }
}