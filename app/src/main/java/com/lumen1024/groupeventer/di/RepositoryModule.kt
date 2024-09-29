package com.lumen1024.groupeventer.di

import com.lumen1024.data.FirebaseGroupRepository
import com.lumen1024.data.FirebaseUserDataRepository
import com.lumen1024.domain.usecase.GroupRepository
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
