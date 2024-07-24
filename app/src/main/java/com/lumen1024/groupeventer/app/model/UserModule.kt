package com.lumen1024.groupeventer.app.model

import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserActions
import com.lumen1024.groupeventer.entities.user.model.FirebaseUserStateHolder
import com.lumen1024.groupeventer.entities.user.model.UserActions
import com.lumen1024.groupeventer.entities.user.model.UserDataRepository
import com.lumen1024.groupeventer.entities.user.model.UserStateHolder
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