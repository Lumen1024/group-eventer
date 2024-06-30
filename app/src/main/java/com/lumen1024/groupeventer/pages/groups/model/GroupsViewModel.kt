package com.lumen1024.groupeventer.pages.groups.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group.model.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _groups = MutableStateFlow(emptyList<Group>())
    val groups = _groups.asStateFlow()

    init {
        viewModelScope.launch {
            val result = groupRepository.getGroups()

            if (result.isSuccess)
                _groups.value = groupRepository.getGroups().getOrElse { emptyList() }


        }
    }

    fun addGroup(name: String, password: String)
    {

    }

}