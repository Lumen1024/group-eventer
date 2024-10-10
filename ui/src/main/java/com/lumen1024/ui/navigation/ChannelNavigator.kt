package com.lumen1024.ui.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel


class ChannelNavigator : Navigator {

    override val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    suspend fun navigateBack(
        route: Screen? = null,
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
        route: Screen? = null,
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
        route: Screen,
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
        route: Screen,
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
        screen: Screen,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        tryNavigateTo(screen, builder)
    }

    override fun back(inclusive: Boolean) {
        tryNavigateBack(inclusive = inclusive)
    }

    override fun deepBack(screen: Screen, inclusive: Boolean) {
        tryNavigateBack(screen, inclusive)
    }
}

sealed class NavigationIntent {

    data class NavigateBack(
        val route: Screen? = null,
        val inclusive: Boolean,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: Screen,
        val builder: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationIntent()
}



