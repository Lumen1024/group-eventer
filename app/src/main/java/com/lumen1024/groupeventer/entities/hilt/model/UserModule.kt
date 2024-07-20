package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.entities.user_data.model.FirebaseUserDataRepository
import com.lumen1024.groupeventer.entities.user_data.model.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImp: FirebaseUserDataRepository): UserDataRepository
}