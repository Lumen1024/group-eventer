package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.entities.group.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GroupModule {

    @Singleton
    @Binds
    abstract fun bindGroupRepository(groupRepositoryImp: FirebaseGroupRepository): GroupRepository
}
