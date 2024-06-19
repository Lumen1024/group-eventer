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
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val isRegister = selectedTabIndex == 1

    val isLoading = viewModel.isLoading.collectAsState()

    val handleAuth = {
        if (isRegister) {
            viewModel.handleRegister(email, name, password)
        } else {
            viewModel.handleLogin(email, password)
        }
    }

    Column {
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                if (isRegister) {
                    NameTextField(value = name, onChange = { name = it })
                }

                EmailTextField(value = email, onChange = { email = it })
                PasswordTextField(value = password, onChange = { password = it })

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Build, "")
                }

                Button(onClick = handleAuth, enabled = !isLoading.value) {
                    if (isLoading.value) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            modifier = Modifier
                                .width(224.dp)
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            text = stringResource(if (isRegister) R.string.sign_in else R.string.sign_up)
                        )
                    }
                }
            }
        }
    }
}

