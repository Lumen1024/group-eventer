package com.lumen1024.groupeventer.entities.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import kotlin.random.Random

@Composable
fun GroupItem(
    group: Group,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(20.dp)
                    .background(Color(group.color.toColorInt()))

            )
            Text(text = group.name)
        }

        val style = MaterialTheme.typography.bodySmall
        Text(
            text = "15 people",
            fontStyle = style.fontStyle,
            fontWeight = style.fontWeight,
            fontSize = style.fontSize,
            color = MaterialTheme.colorScheme.secondary
        )
    }

}


@Preview(heightDp = 800, widthDp = 360, showBackground = true)
@Composable
fun GroupCardPreview() {
    GroupEventerTheme()
    {
        Column(
            Modifier
                .fillMaxSize()
                .padding(18.dp)
                .background(Color.Transparent), verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            repeat(6) {
                GroupItem(
                    group = Group(
                        name = "Dota 2",
                        color = randomColor(),
                        people = listOf("ded", "ded")
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                HorizontalDivider()
            }
        }
    }

}

fun randomColor(alpha: Int = 255) =
    "#" + Color(
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256),
        alpha = alpha
    ).value.toString(16).substring(2, 8)