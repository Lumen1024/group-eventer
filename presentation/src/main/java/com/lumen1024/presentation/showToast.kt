package com.lumen1024.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

fun Context.showToast(message: String)
{
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun ShowToast(message: String)
{
    LocalContext.current.showToast(message)
}

