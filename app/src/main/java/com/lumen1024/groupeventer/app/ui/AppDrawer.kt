package com.lumen1024.groupeventer.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    val drawerController: DrawerController,
) : ViewModel()

@Composable
fun AppDrawerContent() {
    ModalDrawerSheet {
        Text("Groups", modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        repeat(5) {
            NavigationDrawerItem(
                selected = false,
                label = { Text("ded") },
                onClick = {/* todo: drawer item click*/ }
            )
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