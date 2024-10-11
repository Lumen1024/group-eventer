package com.lumen1024.domain.trash

//open class SimpleNotificationService @Inject constructor(
//    @ApplicationContext val context: Context
//) {
//    open val defaultNotificationIcon = R.drawable.logo
//
//    fun sendNotification(
//        title: String,
//        message: String,
//        channelId: String = "default"
//    ) {
//        val notificationBuilder = NotificationCompat.Builder(context, channelId)
//            .setSmallIcon(defaultNotificationIcon)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        val channel = NotificationChannel(
//            channelId,
//            channelId,
//            NotificationManager.IMPORTANCE_DEFAULT
//        )
//
//        notificationManager.createNotificationChannel(channel)
//
//        notificationManager.notify(0, notificationBuilder.build())
//    }
//}


