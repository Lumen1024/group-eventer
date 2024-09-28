package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.data.FirebaseGroupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindGroupRepository(groupRepositoryImp: FirebaseGroupRepository): com.lumen1024.domain.GroupRepository

    @Binds
    abstract fun bindUserDataRepository(userRepositoryImp: FirebaseUserDataRepository): com.lumen1024.domain.UserDataRepository
}
