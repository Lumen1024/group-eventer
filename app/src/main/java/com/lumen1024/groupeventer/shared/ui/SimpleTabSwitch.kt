package com.lumen1024.groupeventer.shared.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme

@Composable
fun SimpleTabSwitch(
    modifier: Modifier = Modifier,
    initialTab: Int = 0,
    onChange: (Int) -> Unit,
    tabs: List<String>,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
) {
    var selectedTabIndex by remember { mutableIntStateOf(initialTab) }

    val setSelectedTabIndex = { index: Int ->
        selectedTabIndex = index
        onChange(index)
    }

    TabSwitch(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        selectedColor = selectedColor,
        unselectedColor = unselectedColor
    ) {
        tabs.forEachIndexed { index, tabName ->
            Tab(
                modifier = Modifier.height(40.dp),
                selected = index == selectedTabIndex,
                onClick = { setSelectedTabIndex(index) },
                selectedContentColor = unselectedColor,
                unselectedContentColor = selectedColor
            ) {
                Text(tabName)
            }
        }
    }
}

@Preview
@Composable
fun SimpleTabSwitchPreview(modifier: Modifier = Modifier) {
    GroupEventerTheme {
        SimpleTabSwitch(onChange = {}, tabs = listOf("Tab 1", "Tab 2"))
    }
}