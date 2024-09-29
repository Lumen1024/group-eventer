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
import com.lumen1024.presentation.screen.Screen

typealias NavHostAnimationScope = AnimatedContentTransitionScope<NavBackStackEntry>

private const val animationDuration = 500

fun NavHostAnimationScope.getRelativeSlideOutTransition(
    left: List<Screen> = emptyList(),
    right: List<Screen> = emptyList(),
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
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut()
}

fun NavHostAnimationScope.getRelativeSlideInTransition(
    left: List<Screen> = emptyList(),
    right: List<Screen> = emptyList(),
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
            durationMillis = animationDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn()
}