package com.lumen1024.groupeventer.pages.events.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.entities.group.model.FirebaseGroupRepository
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val groupRepository: FirebaseGroupRepository,
) : ViewModel() {

    private val groups = emptyList<Group>()

    private val _events = MutableStateFlow(emptyList<GroupEvent>())
    val events = _events.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }
}