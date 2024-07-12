package com.lumen1024.groupeventer.shared.model

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ScaffoldController {
    private val _topBar = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val topBar = _topBar.asStateFlow()

    private val _bottomBar = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val bottomBar = _bottomBar.asStateFlow()

    private val _floatingButton = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val floatingButton = _floatingButton.asStateFlow()


    fun topBar(content: @Composable () -> Unit) {
        if (topBar.value != content) _topBar.value = content
    }

    fun floatingButton(content: @Composable () -> Unit) {
        if (floatingButton.value != content) _floatingButton.value = content
    }

    fun bottomBar(content: @Composable () -> Unit) {
        if (bottomBar.value != content) _bottomBar.value = content
    }

    fun drawer(content: @Composable () -> Unit) {
        // todo
    }
}
