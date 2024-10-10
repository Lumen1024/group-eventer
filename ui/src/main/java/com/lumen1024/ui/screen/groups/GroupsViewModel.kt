package com.lumen1024.ui.screen.groups

import androidx.lifecycle.ViewModel
import com.lumen1024.domain.usecase.UserStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    val userStateHolder: UserStateHolder,
) : ViewModel()