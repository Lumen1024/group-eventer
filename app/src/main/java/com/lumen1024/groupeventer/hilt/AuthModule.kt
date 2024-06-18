package com.lumen1024.groupeventer.hilt

import com.lumen1024.groupeventer.data.AuthService
import com.lumen1024.groupeventer.data.FirebaseAuthService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthService(
        service: FirebaseAuthService
    ): AuthService
}
