package com.lumen1024.groupeventer.entities.auth.model

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthService(service: FirebaseAuthService): com.lumen1024.domain.AuthService
}
