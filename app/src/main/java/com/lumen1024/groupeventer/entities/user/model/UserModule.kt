package com.lumen1024.groupeventer.entities.user.model

import com.lumen1024.domain.AuthService
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
        userDataRepository: com.lumen1024.domain.UserDataRepository,
        authService: com.lumen1024.domain.AuthService,
    ): com.lumen1024.domain.UserStateHolder {
        return FirebaseUserStateHolder(userDataRepository, groupRepository, authService)
    }

    @Provides
    @Singleton
    fun provideUserActions(
        groupRepository: GroupRepository,
        userDataRepository: com.lumen1024.domain.UserDataRepository,
        userStateHolder: com.lumen1024.domain.UserStateHolder,
    ): com.lumen1024.domain.UserActions {
        return FirebaseUserActions(userDataRepository, groupRepository, userStateHolder)
    }
}