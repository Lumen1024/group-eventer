package com.lumen1024.groupeventer.shared.ui.text_field

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.pages.auth.model.EmailErrorState

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (it: String) -> Unit,
    emailErrorState: EmailErrorState,
    focusManager: FocusManager
) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        label = { Text(text = stringResource(R.string.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, "Email") },
        modifier = modifier,
        onValueChange = onChange,
        singleLine = true,
        value = value,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        isError = emailErrorState != EmailErrorState.Normal,
        supportingText = {
            when(emailErrorState)
            {
                EmailErrorState.Normal -> {}
                EmailErrorState.Empty -> Text(stringResource(R.string.empty_field))
                EmailErrorState.WrongFormat -> Text(stringResource(R.string.wrong_format_email))
                EmailErrorState.AlreadyExist -> Text(stringResource(R.string.email_already_exist))
            }

        }
    )
}