package com.lumen1024.domain

import androidx.navigation.NavOptionsBuilder
import com.lumen1024.groupeventer.shared.model.NavigationIntent
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