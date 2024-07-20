package com.lumen1024.groupeventer.pages.create_event.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.app.navigation.Navigator
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventStatus
import com.lumen1024.groupeventer.entities.user_data.model.UserDataService
import com.lumen1024.groupeventer.shared.config.Screen
import com.lumen1024.groupeventer.shared.model.TimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEventViewModel @Inject constructor(
    val userDataService: UserDataService,
    val navigator: Navigator,
) : ViewModel() {
    fun saveEvent(
        group: Group,
        name: String,
        description: String,
        status: GroupEventStatus,
        requestedRanges: List<TimeRange>,
    ) {
        val event = GroupEvent(
            name = name,
            description = description,
            status = status,
            requestedRanges = requestedRanges
        )

        viewModelScope.launch {
            var res = userDataService.createEvent(event, group)
        }


        navigator.tryNavigateTo(Screen.Events)
    }
}