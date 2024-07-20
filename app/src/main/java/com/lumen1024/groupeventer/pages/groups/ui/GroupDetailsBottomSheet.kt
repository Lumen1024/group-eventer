package com.lumen1024.groupeventer.pages.groups.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.ui.GroupColorBadge
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.entities.user.model.UserRepository
import com.lumen1024.groupeventer.shared.ui.Avatar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _admin = MutableStateFlow<UserData?>(null)
    val admin = _admin.asStateFlow()

    private val _users = MutableStateFlow(emptyList<UserData>())
    val users = _users.asStateFlow()

    fun setGroup(group: Group) {

        viewModelScope.launch {
            _users.value = emptyList()
            group.people.forEach {
                userRepository.getData(it).onSuccess { user ->
                    _users.value += user
                }
            }

            _admin.value = null
            userRepository.getData(group.admin).onSuccess { user ->
                _admin.value = user
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsBottomSheet(
    viewModel: GroupDetailsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    group: Group,
    onLeave: () -> Unit,
) {
    val admin by viewModel.admin.collectAsState()
    val users by viewModel.users.collectAsState()

    LaunchedEffect(group) {
        viewModel.setGroup(group)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GroupColorBadge(color = group.color.color)
                    Text(text = group.name)
                }

                Text(
                    text = "${group.people.size + 1} people",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item {
                    UserListItem(userData = admin, textColor = MaterialTheme.colorScheme.primary)
                }

                items(users) {
                    UserListItem(userData = it)
                }
            }

            HorizontalDivider()

            Button(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    onLeave()
                    onDismiss()
                }
            ) {
                Text(text = "Leave group")
            }
        }
    }
}

@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    userData: UserData?,
    textColor: Color = Color.Unspecified,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Avatar(url = userData?.avatarUrl, size = 48.dp)
        Text(text = userData.let {
            if (it == null || it.name.isEmpty()) {
                return@let "Unknown"
            }

            return@let it.name
        }, color = textColor)
    }
}