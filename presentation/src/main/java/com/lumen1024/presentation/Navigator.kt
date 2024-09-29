package com.lumen1024.presentation

import androidx.navigation.NavOptionsBuilder
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