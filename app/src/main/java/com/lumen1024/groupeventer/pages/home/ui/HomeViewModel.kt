package com.lumen1024.groupeventer.pages.home.ui

import androidx.lifecycle.ViewModel
import com.lumen1024.groupeventer.app.navigation.HomeNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val navigator: HomeNavigator,
) : ViewModel()