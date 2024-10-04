package com.lumen1024.ui.screen.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.ui.R
import com.lumen1024.ui.shared.EmailTextField
import com.lumen1024.ui.shared.NameTextField
import com.lumen1024.ui.shared.PasswordTextField
import com.lumen1024.ui.tools.systemPadding

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val isLoading = viewModel.isLoading.collectAsState()
    val nameError by viewModel.nameError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()

    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.systemPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                val tabModifier = Modifier.padding(vertical = 20.dp)
                Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        modifier = tabModifier
                    )
                }
                Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
                    Text(
                        modifier = tabModifier,
                        text = stringResource(R.string.sign_up),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
                    .width(256.dp)
                    .animateContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    selectedTabIndex == 1,
                    enter = fadeIn() + slideInVertically() + expandVertically(clip = false),
                    exit = fadeOut() + slideOutVertically() + shrinkVertically(clip = false)
                ) {
                    NameTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name, onChange = {
                            name = it
                        },
                        nameErrorState = nameError,
                        keyboardAction = ImeAction.Next to { focusManager.moveFocus(FocusDirection.Down) }
                    )
                }

                EmailTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onChange = { email = it },
                    emailErrorState = emailError,
                    keyboardAction = ImeAction.Next to { focusManager.moveFocus(FocusDirection.Down) }
                )
                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onChange = { password = it },
                    passwordErrorState = passwordError
                )

                IconButton(onClick = { viewModel.googleClicked() }) {
                    Icon(imageVector = Icons.Default.Build, "")
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (selectedTabIndex == 0)
                            viewModel.handleLogin(email, password)
                        else
                            viewModel.handleRegister(email, name, password)
                    },
                    enabled = !isLoading.value
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator()
                    } else {
                        AnimatedContent(
                            targetState = selectedTabIndex,
                            transitionSpec = {
                                if (targetState > initialState) {
                                    // If the target number is larger, it slides up and fades in
                                    // while the initial (smaller) number slides up and fades out.
                                    (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                        slideOutVertically { height -> -height } + fadeOut())
                                } else {
                                    // If the target number is smaller, it slides down and fades in
                                    // while the initial number slides down and fades out.
                                    (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                                        slideOutVertically { height -> height } + fadeOut())
                                }.using(
                                    // Disable clipping since the faded slide-in/out should
                                    // be displayed out of bounds.
                                    SizeTransform(clip = false)
                                )
                            }, label = "Sign in/Sign out button transition"
                        ) { selected ->
                            Text(
                                modifier = Modifier
                                    .padding(4.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                text = stringResource(if (selected == 0) R.string.sign_in else R.string.sign_up)
                            )
                        }

                    }
                }
            }
        }
    }
}

