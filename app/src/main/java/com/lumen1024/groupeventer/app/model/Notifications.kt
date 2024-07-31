package com.lumen1024.groupeventer.app.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.lumen1024.groupeventer.R

class FirebaseNotificationsService : Service() {

    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate() {
        super.onCreate()
        // Инициализация Foreground Service
        startForegroundService()
        // Запуск слушателя Firestore
        startFirebaseListener()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Ваш код для обработки старта сервиса
        return START_STICKY // Обеспечивает перезапуск сервиса, если система его завершит
    }

    override fun onDestroy() {
        super.onDestroy()
        // Удаление слушателя при уничтожении сервиса
        firestoreListener.remove()
    }

    private fun startFirebaseListener() {
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("groups")

        firestoreListener = collection.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("ded", "Listen failed.", e)
                return@addSnapshotListener
            }
            sendNotification(title = "Groups Changed", message = "you are ded")

//            for (dc in snapshots!!.documentChanges) {
//                when (dc.type) {
//                    DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED, DocumentChange.Type.REMOVED -> {
//                        // Обработка изменения
//                        handleFirestoreChange(dc)
//                    }
//                }
//            }
        }
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "data_change_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
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

    private fun startForegroundService() {
        val channelId = "foreground_service_channel"
        val channel = NotificationChannel(
            channelId,
            "Foreground Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Firestore Listener Service")
            .setContentText("Listening to Firestore changes")
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

//private fun isServiceRunning(serviceClass: Class<out Service>): Boolean {
//    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
//        if (serviceClass.name == service.service.className) {
//            return true
//        }
//    }
//    return false
//}

class BootBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            // Запуск Foreground Service
            val serviceIntent = Intent(context, FirebaseNotificationsService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent)
        }
    }
}