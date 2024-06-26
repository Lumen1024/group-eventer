package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.entities.user.model.FirebaseUserRepository
import com.lumen1024.groupeventer.entities.user.model.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImp: FirebaseUserRepository): UserRepository
}