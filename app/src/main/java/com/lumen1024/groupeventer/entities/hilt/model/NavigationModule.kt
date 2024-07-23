package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.app.navigation.Navigator
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// TODO deprecated?
@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun provideNavigator(
        authService: AuthService,
    ): Navigator = Navigator()
}