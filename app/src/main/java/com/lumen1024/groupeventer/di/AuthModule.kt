package com.lumen1024.groupeventer.di

import com.lumen1024.data.implementation.service.FirebaseAuthService
import com.lumen1024.domain.service.AuthService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthService(service: FirebaseAuthService): AuthService
}
