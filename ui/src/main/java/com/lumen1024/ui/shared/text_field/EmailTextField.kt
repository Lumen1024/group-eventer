package com.lumen1024.ui.shared.text_field

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.lumen1024.presentation.R

@Composable
fun EmailTextField(
    value: String,
    onChange: (it: String) -> Unit,
    modifier: Modifier = Modifier,
    emailErrorState: EmailErrorState = EmailErrorState.Normal,
    keyboardAction: Pair<ImeAction, KeyboardActionScope.() -> Unit> = ImeAction.Default to { },
) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = keyboardAction.first
        ),
        label = { Text(text = stringResource(R.string.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, "Email") },
        modifier = modifier,
        onValueChange = onChange,
        singleLine = true,
        value = value,
        keyboardActions = KeyboardActions(onNext = keyboardAction.second),
        isError = emailErrorState != EmailErrorState.Normal,
        supportingText = {
            when (emailErrorState) {
                EmailErrorState.Empty -> Text(stringResource(R.string.empty_field))
                EmailErrorState.WrongFormat -> Text(stringResource(R.string.wrong_format_email))
                EmailErrorState.AlreadyExist -> Text(stringResource(R.string.email_already_exist))
                EmailErrorState.Normal -> {}
            }
        }
    )
}