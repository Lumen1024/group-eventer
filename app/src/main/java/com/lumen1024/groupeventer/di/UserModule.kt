package com.lumen1024.groupeventer.di

import com.lumen1024.data.FirebaseUserActions
import com.lumen1024.data.FirebaseUserStateHolder
import com.lumen1024.domain.usecase.AuthService
import com.lumen1024.domain.usecase.GroupRepository
import com.lumen1024.domain.usecase.UserActions
import com.lumen1024.domain.usecase.UserDataRepository
import com.lumen1024.domain.usecase.UserStateHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserStateHolder(
        groupRepository: GroupRepository,
        userDataRepository: UserDataRepository,
        authService: AuthService,
    ): UserStateHolder {
        return com.lumen1024.data.FirebaseUserStateHolder(
            userDataRepository,
            groupRepository,
            authService
        )
    }

    @Provides
    @Singleton
    fun provideUserActions(
        groupRepository: GroupRepository,
        userDataRepository: UserDataRepository,
        userStateHolder: UserStateHolder,
    ): UserActions {
        return com.lumen1024.data.FirebaseUserActions(
            userDataRepository,
            groupRepository,
            userStateHolder
        )
    }
}