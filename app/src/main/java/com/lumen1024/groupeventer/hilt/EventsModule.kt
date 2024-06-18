package com.lumen1024.groupeventer.hilt

import com.google.firebase.Firebase
import com.lumen1024.groupeventer.data.events.EventsRepository
import com.lumen1024.groupeventer.data.events.FirebaseEventsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsModule {

    @Binds
    abstract fun bindEventsRepository(
        eventsRepositoryImp: FirebaseEventsRepository
    ): EventsRepository
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