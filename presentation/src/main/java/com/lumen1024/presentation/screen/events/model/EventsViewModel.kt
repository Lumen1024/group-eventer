package com.lumen1024.presentation.screen.events.model

import androidx.lifecycle.ViewModel
import com.lumen1024.domain.UserStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    val userStateHolder: UserStateHolder,
) : ViewModel()