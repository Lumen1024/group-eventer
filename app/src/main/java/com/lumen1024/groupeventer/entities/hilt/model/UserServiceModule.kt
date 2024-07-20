package com.lumen1024.groupeventer.entities.hilt.model

import android.content.Context
import com.lumen1024.groupeventer.entities.auth.model.AuthService
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.user_data.model.UserDataService
import com.lumen1024.groupeventer.entities.user_data.model.UserDataRepository
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
        userDataRepository: UserDataRepository,
    ): UserDataService {
        return UserDataService(context, authService, groupRepository, userDataRepository)
    }
}