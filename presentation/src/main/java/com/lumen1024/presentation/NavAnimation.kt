package com.lumen1024.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry
import com.lumen1024.presentation.Screen
import com.lumen1024.presentation.getScreen

typealias NavHostAnimationScope = AnimatedContentTransitionScope<NavBackStackEntry>

private const val animationDuration = 500

fun NavHostAnimationScope.getRelativeSlideOutTransition(
    left: List<com.lumen1024.presentation.Screen> = emptyList(),
    right: List<com.lumen1024.presentation.Screen> = emptyList(),
    default: SlideDirection = SlideDirection.Down
): @JvmSuppressWildcards ExitTransition {

    val targetScreen = targetState.destination.getScreen()
    return slideOutOfContainer(
        towards = with(SlideDirection) {
            when (targetScreen) {
                in left -> Right
                in right -> Left
                else -> default
            }
        },
        animationSpec = tween(
            durationMillis = com.lumen1024.presentation.animationDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut()
}

fun NavHostAnimationScope.getRelativeSlideInTransition(
    left: List<com.lumen1024.presentation.Screen> = emptyList(),
    right: List<com.lumen1024.presentation.Screen> = emptyList(),
    default: SlideDirection = SlideDirection.Down
): @JvmSuppressWildcards EnterTransition {

    val initialScreen = initialState.destination.getScreen()
    return slideIntoContainer(
        towards = with(SlideDirection) {
            when (initialScreen) {
                in left -> Left
                in right -> Right
                else -> default
            }
        },
        animationSpec = tween(
            durationMillis = com.lumen1024.presentation.animationDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn()
}