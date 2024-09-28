package com.lumen1024.groupeventer.shared.model

import androidx.navigation.NavOptionsBuilder
import com.lumen1024.presentation.Screen
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

interface Navigator {
    val navigationChannel: Channel<NavigationIntent>
    fun navigate(
        screen: com.lumen1024.presentation.Screen,
        builder: NavOptionsBuilder.() -> Unit = { }
    )
    fun back(inclusive: Boolean = false)
    fun deepBack(screen: com.lumen1024.presentation.Screen, inclusive: Boolean = false)
}

class ChannelNavigator : Navigator {

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    suspend fun navigateBack(
        route: com.lumen1024.presentation.Screen? = null,
        inclusive: Boolean = false
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    fun tryNavigateBack(
        route: com.lumen1024.presentation.Screen? = null,
        inclusive: Boolean = false
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    suspend fun navigateTo(
        route: com.lumen1024.presentation.Screen,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                builder = builder,
            )
        )
    }

    fun tryNavigateTo(
        route: com.lumen1024.presentation.Screen,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                builder = builder
            )
        )
    }

    override fun navigate(
        screen: com.lumen1024.presentation.Screen,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        tryNavigateTo(screen, builder)
    }

    override fun back(inclusive: Boolean) {
        tryNavigateBack(inclusive = inclusive)
    }

    override fun deepBack(screen: com.lumen1024.presentation.Screen, inclusive: Boolean) {
        tryNavigateBack(screen, inclusive)
    }
}

sealed class NavigationIntent {

    data class NavigateBack(
        val route: com.lumen1024.presentation.Screen? = null,
        val inclusive: Boolean,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: com.lumen1024.presentation.Screen,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationIntent()
}



