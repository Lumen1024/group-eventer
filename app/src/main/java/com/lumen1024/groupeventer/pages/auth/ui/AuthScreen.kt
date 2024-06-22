package com.lumen1024.groupeventer.pages.auth.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.pages.auth.model.AuthViewModel
import com.lumen1024.groupeventer.shared.ui.EmailTextField
import com.lumen1024.groupeventer.shared.ui.NameTextField
import com.lumen1024.groupeventer.shared.ui.PasswordTextField

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(
                selectedTabIndex = if (state.value.isLogin) 0 else 1
            ) {
                Tab(selected = false, onClick = { viewModel.onTabClicked(0) }) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
                Tab(selected = false, onClick = { viewModel.onTabClicked(1) }) {
                    Text(text = stringResource(R.string.sign_up))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                if (!state.value.isLogin) {
                    NameTextField(
                        value = state.value.name, onChange = {
                            viewModel.onNameEdit(it)
                        },
                        state.value.nameErrorState,
                        focusManager
                    )

                }

                EmailTextField(
                    modifier = Modifier.padding(vertical = 10.dp),
                    value = state.value.email,
                    onChange = { viewModel.onEmailEdit(it) },
                    emailErrorState = state.value.emailErrorState,
                    focusManager = focusManager
                )
                PasswordTextField(
                    value = state.value.password,
                    onChange = { viewModel.onPasswordEdit(it) },
                    passwordErrorState = state.value.passwordErrorState
                )

                IconButton(onClick = { viewModel.googleClicked() }) {
                    Icon(imageVector = Icons.Default.Build, "")
                }

                Button(
                    onClick = { viewModel.onConfirmClicked() },
                    enabled = !state.value.isLoading
                ) {
                    if (state.value.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            modifier = Modifier
                                .width(224.dp)
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            text = stringResource(if (state.value.isLogin) R.string.sign_in else R.string.sign_up)
                        )
                    }
                }

            }
        }
    }
}

