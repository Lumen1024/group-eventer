package com.lumen1024.groupeventer.di

import com.lumen1024.groupeventer.shared.model.Navigator
import com.lumen1024.presentation.ChannelNavigator
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
    fun provideNavigator(): Navigator = ChannelNavigator()
}