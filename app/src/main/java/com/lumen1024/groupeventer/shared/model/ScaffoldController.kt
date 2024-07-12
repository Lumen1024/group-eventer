package com.lumen1024.groupeventer.shared.model

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class ScaffoldController {
    private val _topBar = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val topBar = _topBar.asStateFlow()

    private val _bottomBar = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val bottomBar = _bottomBar.asStateFlow()

    private val _floatingActionButton = MutableStateFlow<(@Composable () -> Unit)?>(null)
    val floatingActionButton = _floatingActionButton.asStateFlow()

    fun topBar(content: @Composable () -> Unit) {
        if (topBar.value != content) _topBar.value = content
    }

    fun bottomBar(content: @Composable () -> Unit) {
        if (bottomBar.value != content) _bottomBar.value = content
    }

    fun floatingActionButton(content: @Composable () -> Unit) {
        if (floatingActionButton.value != content) _floatingActionButton.value = content
    }

    fun setup(
        topBar: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        clear: Boolean = true,
    ) {
        if (clear) {
            this.topBar {}
            this.bottomBar {}
            this.floatingActionButton {}
        }

        this.topBar(topBar)
        this.bottomBar(bottomBar)
        this.floatingActionButton(floatingActionButton)
    }

    fun drawer(content: @Composable () -> Unit) {
        // todo
    }
}
