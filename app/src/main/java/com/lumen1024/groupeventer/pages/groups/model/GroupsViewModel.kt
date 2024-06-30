package com.lumen1024.groupeventer.pages.groups.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import com.lumen1024.groupeventer.entities.group.model.GroupRepositoryException
import com.lumen1024.groupeventer.entities.group.model.toMessage
import com.lumen1024.groupeventer.entities.user.model.UserData
import com.lumen1024.groupeventer.entities.user.model.UserRepository
import com.lumen1024.groupeventer.shared.lib.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebase: Firebase,
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _groups = MutableStateFlow(emptyList<Group>())
    val groups = _groups.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData = _userData.asStateFlow()

    init {
        getGroups()
    }

    fun getGroups() {
        viewModelScope.launch {
            if (firebase.auth.currentUser !== null) {
                val userData = userRepository.getData(firebase.auth.currentUser!!.uid)

                _userData.value = userData.getOrNull()

                if (userData.isSuccess && userData.getOrNull() !== null) {
                    val result =
                        groupRepository.getGroups(
                            userData.getOrNull()!!.groups.keys.toList()
                        )

                    if (result.isSuccess) {
                        _groups.value = result.getOrElse { emptyList() }
                    } else {
                        val exception = result.exceptionOrNull()
                        if (exception !== null && exception is GroupRepositoryException) {
                            context.showToast(context.resources.getString(exception.toMessage()))
                        }
                    }
                }
            }
        }
    }

    fun toggleGroupHide(groupId: String) {
        val hidden = userData.value?.groups?.get(groupId)

        if (firebase.auth.currentUser !== null && hidden !== null) {
            viewModelScope.launch {
                userRepository.updateData(
                    firebase.auth.currentUser!!.uid, data = mapOf(
                        "groups" to mapOf<String, Any>(
                            groupId to !hidden
                        )
                    )
                )

                getGroups()
            }
        }
    }

    fun addGroup(name: String, password: String) {

    }

}