package com.lumen1024.groupeventer.entities.hilt.model

import android.content.Context
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.user.model.UserService
import com.lumen1024.groupeventer.entities.user.model.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserServiceModule {

    @Provides
    @Singleton
    fun provideUserService(
        @ApplicationContext context: Context,
        authService: AuthService,
        groupRepository: GroupRepository,
        userRepository: UserRepository,
    ): UserService {
        return UserService(context, authService, groupRepository, userRepository)
    }
}