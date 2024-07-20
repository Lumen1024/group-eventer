package com.lumen1024.groupeventer.pages.events.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumen1024.groupeventer.app.navigation.Navigator
import com.lumen1024.groupeventer.entities.user_data.model.UserDataService
import com.lumen1024.groupeventer.shared.config.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val userDataService: UserDataService,
    private val navigator: Navigator,
) : ViewModel() {

    init {
        viewModelScope.launch {

        }
    }

    fun addEvent() {
        navigator.tryNavigateTo(Screen.CreateEvent)
    }
}