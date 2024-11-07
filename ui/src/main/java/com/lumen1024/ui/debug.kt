package com.lumen1024.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// 1) can change only in viewmodel
// 2) without useless recomposition
// 3)

// region ded
annotation class UiStateClass(val value: String = "Default value")

@UiStateClass
data class AuthScreenUiState(
    var name: String = "ded",
    val email: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val confirmPassword: MutableState<String> = mutableStateOf(""),
)


data class AuthScreenUiStatea(
    var name: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val confirmPassword: MutableState<String> = mutableStateOf(""),
)
// endregion ded

data class DefaultState(
    val first: Int = 0,
    val second: Int = 0,
    val color: Color = Color.Red,
    val collect: List<Int> = listOf(3, 2, 1)
)

private class StateViewModel() {
    private val _state = MutableStateFlow(DefaultState())
    val state = _state.asStateFlow()

    fun onClick() {
        _state.value = _state.value.copy(first = _state.value.first + 1)
    }
}

@Composable
fun SomeScreen(modifier: Modifier = Modifier) {
    val viewmodel = StateViewModel()
    val state by viewmodel.state.collectAsState()
    Surface(
        modifier = Modifier
            .width(300.dp)
            .height(600.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textModifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(state.color)
                .padding(20.dp)
            Text(
                modifier = textModifier
                    .clickable { viewmodel.onClick() },
                textAlign = TextAlign.Center,
                text = state.first.toString()
            )
            Spacer(Modifier.height(50.dp))
            Text(
                modifier = textModifier,
                textAlign = TextAlign.Center,
                text = state.second.toString()
            )
        }
    }
}

@Composable
fun AnimationTest(modifier: Modifier = Modifier) {
    var offset1 by remember { mutableFloatStateOf(1f) }
    val offset by animateFloatAsState(
        targetValue = offset1,
        label = "offset animation"
    )
    Box(
        modifier
            .size(200.dp)
            .scale(offset)
            .graphicsLayer {
                scaleX = offset
                scaleY = offset
            }
            .background(Color.Cyan)
            .clickable { offset1 /= 1.1f })
}

@Preview
@Composable
private fun Playground() {
    AnimationTest()
    //SomeScreen()
}