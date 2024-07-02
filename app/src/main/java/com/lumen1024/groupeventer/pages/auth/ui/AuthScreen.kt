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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.pages.auth.model.AuthViewModel
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.ui.EmailTextField
import com.lumen1024.groupeventer.shared.ui.NameTextField
import com.lumen1024.groupeventer.shared.ui.PasswordTextField

@Composable
fun AuthScreen(
    mainNavController: NavHostController,
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

    val navigateHome by viewModel.navigateHome.collectAsState()
    if (navigateHome)
        mainNavController.navigate(Screen.Home.route)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                Tab(selected = false, onClick = { selectedTabIndex = 0 }) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
                Tab(selected = false, onClick = { selectedTabIndex = 1 }) {
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
                if (selectedTabIndex == 1) {
                    NameTextField(
                        value = name, onChange = {
                            name = it
                        },
                        nameErrorState = nameError,
                        focusManager = focusManager
                    )
                }

                EmailTextField(
                    modifier = Modifier.padding(vertical = 10.dp),
                    value = email,
                    onChange = { email = it },
                    emailErrorState = emailError,
                    focusManager = focusManager
                )
                PasswordTextField(
                    value = password,
                    onChange = { password = it },
                    passwordErrorState = passwordError
                )

                IconButton(onClick = { viewModel.googleClicked() }) {
                    Icon(imageVector = Icons.Default.Build, "")
                }

                Button(
                    onClick = {
                        if (selectedTabIndex == 0)
                            viewModel.handleLogin(email,password)
                        else
                            viewModel.handleRegister(email,name,password)
                    },
                    enabled = !isLoading.value
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            modifier = Modifier
                                .width(224.dp)
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            text = stringResource(if (isLoading.value) R.string.sign_in else R.string.sign_up)
                        )
                    }
                }

            }
        }
    }
}

