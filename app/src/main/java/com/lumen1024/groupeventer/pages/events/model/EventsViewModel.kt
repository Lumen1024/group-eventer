package com.lumen1024.groupeventer.pages.events.model

import androidx.lifecycle.ViewModel
import com.lumen1024.groupeventer.entities.user.model.UserStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    val userStateHolder: UserStateHolder,
) : ViewModel()