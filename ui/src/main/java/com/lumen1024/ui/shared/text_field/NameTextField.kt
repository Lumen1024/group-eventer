package com.lumen1024.ui.shared.text_field

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.lumen1024.presentation.R

@Composable
fun NameTextField(
    value: String,
    onChange: (it: String) -> Unit,
    modifier: Modifier = Modifier,
    nameErrorState: NameErrorState = NameErrorState.Normal,
    keyboardAction: Pair<ImeAction, KeyboardActionScope.() -> Unit> = ImeAction.Default to { },
    placeholder: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        label = { Text(text = stringResource(R.string.name)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, "Email") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        keyboardActions = KeyboardActions(onNext = keyboardAction.second),
        isError = nameErrorState != NameErrorState.Normal,
        placeholder = placeholder,
        supportingText = {
            when (nameErrorState) {
                NameErrorState.Empty -> Text(stringResource(R.string.empty_field))
                NameErrorState.Normal -> {}
            }
        }
    )
}