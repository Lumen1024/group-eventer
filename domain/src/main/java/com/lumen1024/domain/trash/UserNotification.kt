package com.lumen1024.domain.trash

//interface UserNotification {
//
//    // TODO: maybe best option is not use many method
//    //       and create enum for different event change?
//
//    fun notifyEventCreated(eventId: String, groupId: String)
//
//    fun notifyEventTimePlaced(eventId: String, groupId: String)
//
//    fun notifyEventChanged(eventId: String, groupId: String)
//}
//
//class TodoImp : UserNotification {
//    override fun notifyEventCreated(eventId: String, groupId: String) {
//        // TODO: implement
//    }
//
//    override fun notifyEventTimePlaced(eventId: String, groupId: String) {
//        // TODO: implement
//    }
//
//    override fun notifyEventChanged(eventId: String, groupId: String) {
//        // TODO: implement
//    }
//
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//object UserNotificationModule {
//
//    @Singleton
//    @Provides
//    fun provideUserNotifications(): UserNotification = TodoImp()
//}