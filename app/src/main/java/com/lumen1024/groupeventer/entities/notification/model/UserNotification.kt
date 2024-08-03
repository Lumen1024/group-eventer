package com.lumen1024.groupeventer.entities.notification.model

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface UserNotification {

    // todo: maybe best option is not use many method
    //       and create enum for different event change?

    fun notifyEventCreated(eventId: String, groupId: String)

    fun notifyEventTimePlaced(eventId: String, groupId: String)

    fun notifyEventChanged(eventId: String, groupId: String)
}

class TodoImp : UserNotification {
    override fun notifyEventCreated(eventId: String, groupId: String) {
        // todo
    }

    override fun notifyEventTimePlaced(eventId: String, groupId: String) {
        // todo
    }

    override fun notifyEventChanged(eventId: String, groupId: String) {
        // todo
    }

}

@Module
@InstallIn(SingletonComponent::class)
object UserNotificationModule {

    @Singleton
    @Provides
    fun provideUserNotifications(): UserNotification = TodoImp()
}