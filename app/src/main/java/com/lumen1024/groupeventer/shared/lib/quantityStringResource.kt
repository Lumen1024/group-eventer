package com.lumen1024.groupeventer.shared.lib

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource

@Composable
fun quantityStringResource(@PluralsRes id: Int, quantity: Int): String {
    return pluralStringResource(id, quantity) //LocalContext.current.resources.getQuantityString(id, quantity, quantity)
}