package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.pages.auth.model.PasswordErrorState


@Composable
fun PasswordTextField(
    value: String,
    onChange: (it: String) -> Unit,
    passwordErrorState: PasswordErrorState
) {
    var hided by remember {
        mutableStateOf(true)
    }
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = stringResource(R.string.password)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, "Password") },
        modifier = Modifier,
        onValueChange = onChange,
        singleLine = true,
        value = value,
        visualTransformation = if (hided) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { hided = !hided }) {
                Icon(
                    imageVector = if (hided) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = ""
                )
            }
        },
        isError = passwordErrorState != PasswordErrorState.Normal,
        supportingText = {
            when(passwordErrorState)
            {
                PasswordErrorState.Normal -> {}
                PasswordErrorState.Empty -> Text(stringResource(R.string.empty_field))
                PasswordErrorState.Short -> Text(stringResource(R.string.short_password))
            }
        }
    )
}