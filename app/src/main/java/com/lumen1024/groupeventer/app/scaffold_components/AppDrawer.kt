package com.lumen1024.groupeventer.app.scaffold_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton

class DrawerController {

    val drawerChannel = Channel<DrawerValue>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    fun tryOpenDrawer() {
        drawerChannel.trySend(DrawerValue.Open)
    }

    fun tryCloseDrawer() {
        drawerChannel.trySend(DrawerValue.Closed)
    }
}

@HiltViewModel
class DrawerViewModel @Inject constructor(
    val drawerController: DrawerController
) : ViewModel()

@Composable
fun AppDrawerContent() {
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .statusBarsPadding()
        //.padding(start = 8.dp)
        .fillMaxWidth(0.9f)
        .fillMaxHeight()
    ) {
        repeat(5) {
            Text(text = "ded")
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DrawerModule {
    @Provides
    @Singleton
    fun provideDrawerController() = DrawerController()
}