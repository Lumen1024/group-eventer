package com.lumen1024.groupeventer.app.scaffold_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState

sealed class TopBarVariant {
    data object None : TopBarVariant()
    data class Search(
        val hint: String,
        val onSearch: (String) -> Unit = {},
    ) : TopBarVariant()

    data class Default(
        val title: String,
        val navIcon: Pair<ImageVector, () -> Unit>? = null,
        val actions: Map<ImageVector, () -> Unit> = emptyMap(),
    ) : TopBarVariant()
}

fun Screen.getTopBarVariant(): TopBarVariant {
    return when (this) {
        Screen.Events -> TopBarVariant.Search(hint = "Search events") { }

        Screen.Groups -> TopBarVariant.Default(
            title = "Groups", // todo res
            navIcon = Icons.AutoMirrored.Filled.List to {}, // todo
            actions = mapOf(
                Icons.Default.AccountCircle to {}, // todo
            )
        )

        Screen.Profile -> TopBarVariant.Default(
            title = "Profile", // todo res
            navIcon = Icons.AutoMirrored.Filled.List to {}, // todo
            actions = mapOf(
                Icons.Default.Settings to {}, // todo
            )
        )

        else -> TopBarVariant.None
    }
}

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val topBarVariant by remember {
        derivedStateOf {
            currentScreen?.getTopBarVariant() ?: TopBarVariant.None
        }
    }

    // TODO performance?
    AnimatedContent(targetState = topBarVariant, label = "Top bar animation") {
        when (val variant = it) {
            is TopBarVariant.Search -> {

            }

            is TopBarVariant.Default -> {
                DefaultTopBar(variant = variant, modifier = modifier)
            }

            else -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    variant: TopBarVariant.Default,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = variant.title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = variant.navIcon?.let {
            return@let {
                IconButton(onClick = it.second) {
                    Icon(imageVector = it.first, contentDescription = "") // todo ?
                }
            }
        } ?: { },
        actions = {
            variant.actions.forEach { pair ->
                IconButton(onClick = pair.value) {
                    Icon(imageVector = pair.key, contentDescription = "") // todo ?
                }
            }
        }

    )
}