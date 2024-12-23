package com.lumen1024.groupeventer.di

import com.lumen1024.data.implementation.repository.FirebaseEventRepository
import com.lumen1024.data.implementation.repository.FirebaseGroupRepository
import com.lumen1024.data.implementation.repository.FirebaseUserRepository
import com.lumen1024.domain.repository.EventRepository
import com.lumen1024.domain.repository.GroupRepository
import com.lumen1024.domain.repository.UserRepository
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
    abstract fun bindUserRepository(userRepositoryImp: FirebaseUserRepository): UserRepository

    @Binds
    abstract fun bindEventRepository(eventRepositoryImp: FirebaseEventRepository): EventRepository
}
