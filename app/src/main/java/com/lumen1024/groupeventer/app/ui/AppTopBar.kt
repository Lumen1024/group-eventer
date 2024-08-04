package com.lumen1024.groupeventer.app.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.lib.getCurrentScreenAsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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

fun Screen.getTopBarVariant(drawerController: DrawerController): TopBarVariant { // TODO: ref
    return when (this) {
        Screen.Groups -> TopBarVariant.Default(
            title = "Groups", // TODO: res
            navIcon = Icons.AutoMirrored.Filled.List to { drawerController.tryOpenDrawer() },
            actions = mapOf(
                Icons.Default.AccountCircle to {}, // TODO: top bar account circle click handler
            )
        )

        Screen.Profile -> TopBarVariant.Default(
            title = "Profile", // TODO: res
            navIcon = Icons.AutoMirrored.Filled.List to { drawerController.tryOpenDrawer() },
            actions = mapOf(
                Icons.Default.Settings to {}, // TODO: setting topBar handler
            )
        )

        Screen.Events -> TopBarVariant.Search(
            hint = "Search events", // TODO: res
            onSearch = {} // TODO: onSearch handler
        )

        else -> TopBarVariant.None
    }
}

@HiltViewModel
class TopAppBarViewModel @Inject constructor(
    val drawerController: DrawerController,
) : ViewModel()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: TopAppBarViewModel = hiltViewModel(),
) {
    val currentScreen by navController.getCurrentScreenAsState()
    val topBarVariant by remember {
        derivedStateOf {
            currentScreen?.getTopBarVariant(viewModel.drawerController) ?: TopBarVariant.None
        }
    }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        // TODO: performance?  // ??????????
        AnimatedContent(
            targetState = topBarVariant,
            label = "Top bar animation"
        ) { variant -> // TODO: ???????????
            when (variant) {
                is TopBarVariant.Search -> {
                    var searchActive by remember { mutableStateOf(false) }
                    var query by remember { mutableStateOf("") }
                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = { variant.onSearch(it); searchActive = false },
                        active = searchActive,
                        onActiveChange = { searchActive = it },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        },
                        placeholder = { Text(variant.hint) }
                    ) {
                        // TODO: content inside search?
                    }
                }

                is TopBarVariant.Default -> {
                    DefaultTopBar(variant = variant, modifier = modifier)
                }

                else -> {}
            }
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
                    Icon(imageVector = it.first, contentDescription = "") // TODO: ?
                }
            }
        } ?: { },
        actions = {
            variant.actions.forEach { pair ->
                IconButton(onClick = pair.value) {
                    Icon(imageVector = pair.key, contentDescription = "") // TODO: ?
                }
            }
        }

    )
}