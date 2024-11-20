package com.lumen1024.ui

import android.content.Context
import com.lumen1024.ui.tools.showToast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
object ToastModule {
    @Provides
    fun provideToastService(@ApplicationContext context: Context): ToastService {
        return ToastService(context)
    }
}

class ToastService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun showToast(message: String) {
        context.showToast(message)
    }
}