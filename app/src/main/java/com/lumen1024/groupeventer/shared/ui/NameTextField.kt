package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.shared.model.NameErrorState

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (it: String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    nameErrorState: NameErrorState = NameErrorState.Normal,
    focusManager: FocusManager? = null,
) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        label = { Text(text = stringResource(R.string.name)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, "Email") },
        modifier = modifier,
        onValueChange = onChange,
        value = value,
        singleLine = true,
        placeholder = placeholder,
        keyboardActions = KeyboardActions(onNext = { focusManager?.moveFocus(FocusDirection.Down) }),
        isError = nameErrorState != NameErrorState.Normal,
        supportingText = {
            when (nameErrorState) {
                NameErrorState.Normal -> {}
                NameErrorState.Empty -> Text(stringResource(R.string.empty_field))
            }
        }
    )
}