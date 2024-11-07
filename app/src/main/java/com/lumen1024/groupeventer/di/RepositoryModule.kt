package com.lumen1024.groupeventer.di

import com.lumen1024.data.implementation.FirebaseGroupRepository
import com.lumen1024.data.implementation.FirebaseUserDataRepository
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.usecase.UserDataRepository
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
