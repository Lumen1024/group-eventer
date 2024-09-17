package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
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
        return FirebaseUserStateHolder(userDataRepository, groupRepository, authService)
    }

    @Provides
    @Singleton
    fun provideUserActions(
        groupRepository: GroupRepository,
        userDataRepository: UserDataRepository,
        userStateHolder: UserStateHolder,
    ): UserActions {
        return FirebaseUserActions(userDataRepository, groupRepository, userStateHolder)
    }
}