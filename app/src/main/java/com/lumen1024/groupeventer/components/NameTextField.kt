package com.lumen1024.groupeventer.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.R

@Composable
fun NameTextField(value: String, onChange: (it: String) -> Unit) {
    TextField(
        label = { Text(text = stringResource(R.string.name)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, "Email") },
        modifier = Modifier.height(56.dp),
        onValueChange = onChange,
        value = value,
    )
}