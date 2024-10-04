package com.lumen1024.ui.screen.events

import androidx.lifecycle.ViewModel
import com.lumen1024.domain.usecase.UserStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    val userStateHolder: UserStateHolder,
) : ViewModel()