package com.lumen1024.groupeventer.app.navigation

import com.lumen1024.groupeventer.shared.config.Screen
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class Navigator {

    val navigationChannel = Channel<NavigationIntent>(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    suspend fun navigateBack(route: Screen? = null, inclusive: Boolean = false) {
        navigationChannel.send(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    fun tryNavigateBack(route: Screen? = null, inclusive: Boolean = false) {
        navigationChannel.trySend(
            NavigationIntent.NavigateBack(
                route = route,
                inclusive = inclusive
            )
        )
    }

    suspend fun navigateTo(
        route: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    ) {
        navigationChannel.send(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }

    fun tryNavigateTo(
        route: Screen,
        popUpToRoute: Screen? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    ) {
        navigationChannel.trySend(
            NavigationIntent.NavigateTo(
                route = route,
                popUpToRoute = popUpToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop,
            )
        )
    }

    // TODO implement restore state
}

sealed class NavigationIntent {
    data class NavigateBack(
        val route: Screen? = null,
        val inclusive: Boolean = false,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationIntent()
}

