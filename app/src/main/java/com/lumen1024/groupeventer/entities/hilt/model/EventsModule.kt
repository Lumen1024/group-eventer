package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.entities.group_event.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group_event.model.GroupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsModule {

    @Binds
    abstract fun bindEventsRepository(eventsRepositoryImp: FirebaseGroupRepository): GroupRepository
}
