package com.lumen1024.groupeventer.entities.hilt.model

import com.lumen1024.groupeventer.app.navigation.HomeNavigator
import com.lumen1024.groupeventer.app.navigation.MainNavigator
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Singleton
    @Provides
    fun provideMainNavigator(
        authService: AuthService,
    ): MainNavigator = MainNavigator(authService)

    @Singleton
    @Provides
    fun provideHomeNavigator(

    ): HomeNavigator = HomeNavigator()
}