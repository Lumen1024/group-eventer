package com.lumen1024.groupeventer.hilt

import com.google.firebase.Firebase
import com.lumen1024.groupeventer.data.events.FirebaseGroupRepository
import com.lumen1024.groupeventer.data.events.GroupRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsModule {

    @Binds
    abstract fun bindEventsRepository(
        eventsRepositoryImp: FirebaseGroupRepository
    ): GroupRepository
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebase(
        // Potential dependencies of this type
    ): Firebase {
        return Firebase
    }
}