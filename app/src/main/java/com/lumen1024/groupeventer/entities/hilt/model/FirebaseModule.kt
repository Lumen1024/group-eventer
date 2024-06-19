package com.lumen1024.groupeventer.entities.hilt.model

import com.google.firebase.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    
    @Provides
    fun provideFirebase(): Firebase {
        return Firebase
    }
}