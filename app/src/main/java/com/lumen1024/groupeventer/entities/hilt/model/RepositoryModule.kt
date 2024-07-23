package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.entities.group.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserDataRepository
import com.lumen1024.groupeventer.entities.user.model.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGroupRepository(groupRepositoryImp: FirebaseGroupRepository): GroupRepository

    @Binds
    abstract fun bindUserDataRepository(userRepositoryImp: FirebaseUserDataRepository): UserDataRepository
}
