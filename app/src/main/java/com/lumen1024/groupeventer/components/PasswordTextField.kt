package com.lumen1024.groupeventer.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.R


@Composable
fun PasswordTextField(value: String, onChange: (it: String) -> Unit) {
    TextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        label = { Text(text = stringResource(R.string.password)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, "Password") },
        modifier = Modifier.height(56.dp),
        onValueChange = onChange,
        singleLine = true,
        value = value,
        visualTransformation = PasswordVisualTransformation(),
    )
}