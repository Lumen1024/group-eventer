package com.lumen1024.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.ui.config.GroupEventerTheme

@Composable
fun TabSelect(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    startedSelected: Int = 0,
    onSelect: (index: Int) -> Unit = {},
) {
    var selectedIndex by remember { mutableIntStateOf(startedSelected) }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            //.background(Color.Yellow)
            //.padding(16.dp)
            .border(
                width = 1.dp,
                MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .clipToBounds(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, item ->
            val bgColor = if (selectedIndex == index) Color.Blue else Color.Red
            Box(
                modifier = Modifier
                    .background(bgColor)
                    .padding(8.dp)
                    .clickable { onSelect(index); selectedIndex = index }

            ) {
                Text(text = item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabSelectPreview() {
    val tabs = listOf(
        "Join",
        "Create",
        "Other"
    )

    GroupEventerTheme {
        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(20.dp),
            contentAlignment = Alignment.Center


        ) {
            TabSelect(
                modifier = Modifier,
                tabs = tabs
            )
        }
    }
}