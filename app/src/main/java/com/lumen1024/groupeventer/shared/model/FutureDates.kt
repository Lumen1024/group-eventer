package com.lumen1024.groupeventer.shared.model

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates

@OptIn(ExperimentalMaterial3Api::class)
object FutureDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }
}