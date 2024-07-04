package com.lumen1024.groupeventer.pages.groups.ui

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lumen1024.groupeventer.entities.group.ui.GroupItem
import com.lumen1024.groupeventer.pages.auth.model.NameErrorState
import com.lumen1024.groupeventer.pages.auth.model.PasswordErrorState
import com.lumen1024.groupeventer.pages.groups.model.GroupsViewModel
import com.lumen1024.groupeventer.shared.ui.NameTextField
import com.lumen1024.groupeventer.shared.ui.PasswordTextField
import com.lumen1024.groupeventer.shared.ui.PickerDialog


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel = hiltViewModel(),
) {
    val groups = viewModel.groups.collectAsState()
    val userData = viewModel.userData.collectAsState()

    val addDialogOpen = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
//            SearchBar(query = , onQueryChange = , onSearch = , active = , onActiveChange = ) {
//
//            }

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                addDialogOpen.value = true
            }) {
                Icon(Icons.Default.Add, "")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(groups.value, key = { group -> group.id })
            {
                GroupItem(
                    it
                )
            }
        }

        if (addDialogOpen.value)
            AddGroupDialog(
                onDismiss = { addDialogOpen.value = false },
                onConfirm = { name, password -> viewModel.addGroup(name, password) }
            )
    }
}

@Composable
fun AddGroupDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, password: String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(NameErrorState.Normal) }
    var passwordError by remember { mutableStateOf(PasswordErrorState.Normal) }

    val handleNameErrors = {
        nameError = if (name.isEmpty()) NameErrorState.Empty else NameErrorState.Normal
    }
    val handlePasswordErrors = {
        passwordError =
            if (password.isEmpty()) PasswordErrorState.Empty else PasswordErrorState.Normal
    }


    val handleErrors = {

        handleNameErrors()
        handlePasswordErrors
    }
    PickerDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add group") }, // todo res
        buttons = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(R.string.cancel)) // todo res
            }
            TextButton(
                onClick = {
                    handleErrors()
                    if (name.isNotEmpty() && password.isNotEmpty()) {
                        onConfirm(name, password)
                        onDismiss()
                    }
                }) {
                Text(text = stringResource(R.string.ok)) // todo res
            }
        }
    ) {

        Column(Modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.End)
        {
            NameTextField(
                value = name, onChange = {
                    name = it
                    handleErrors()
                },
                nameErrorState = nameError
            )
            PasswordTextField(
                value = password, onChange = {
                    password = it
                    handleErrors()
                },
                passwordErrorState = passwordError
            )
        }

    }
}
