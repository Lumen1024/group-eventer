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
import com.lumen1024.groupeventer.shared.model.PasswordErrorState


@Composable
fun PasswordTextField(
    value: String,
    onChange: (it: String) -> Unit,
    modifier: Modifier = Modifier,
    passwordErrorState: PasswordErrorState = PasswordErrorState.Normal,
) {
    var hided by remember { mutableStateOf(true) }

    OutlinedTextField(
        onValueChange = onChange,
        value = value,
        modifier = modifier,
        singleLine = true,
        label = { Text(text = stringResource(R.string.password)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (hided) PasswordVisualTransformation() else VisualTransformation.None,
        isError = passwordErrorState != PasswordErrorState.Normal,
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, "Password") },
        trailingIcon = {
            IconButton(onClick = { hided = !hided }) {
                Icon(
                    imageVector = if (hided) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = ""
                )
            }
        },
        supportingText = {
            when (passwordErrorState) {
                PasswordErrorState.Normal -> {}
                PasswordErrorState.Empty -> Text(stringResource(R.string.empty_field))
                PasswordErrorState.Short -> Text(stringResource(R.string.short_password))
            }
        }
    )
}