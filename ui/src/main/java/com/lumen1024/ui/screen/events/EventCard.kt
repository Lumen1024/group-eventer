package com.lumen1024.ui.screen.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.Event
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupEventStatus
import com.lumen1024.domain.data.TimeRange
import com.lumen1024.domain.tools.toStringRelative
import com.lumen1024.ui.config.GroupEventerTheme
import java.time.Instant

@Composable
fun EventCard(
    pair: Pair<Event, Group>,
    onOptionClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    // TODO: is remember necessary?
    val event by remember(pair.first) { derivedStateOf { pair.first } }
    val group by remember(pair.second) { derivedStateOf { pair.second } }

    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        // main container
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                // group title
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FromGroupText(group.name, Color(group.color.hex))
                    val titleStyle = MaterialTheme.typography.titleLarge
                    Text(
                        text = event.name + if (event.status == GroupEventStatus.Finish) " (завершен)" else "",
                        fontStyle = titleStyle.fontStyle,
                        fontWeight = titleStyle.fontWeight,
                        fontSize = titleStyle.fontSize,
                    )
                }
                IconButton(
                    modifier = Modifier.offset(x = 12.dp, y = (-12).dp),
                    onClick = onOptionClicked,
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                }
            }
            // footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (event.status == GroupEventStatus.Voting)
                    EventCardTimeRange(timeRange = event.initialRange!!)
                else
                    EventCardTime(time = event.startTime!!)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "${event.duration.toHours()}h")
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

    }
}

@Composable
fun EventCardTimeRange(
    modifier: Modifier = Modifier,
    timeRange: TimeRange, // TODO: click to vote?
) {
    Text(
        modifier = modifier
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 1.dp),
        text = timeRange.toString()
    )
}

@Composable
fun EventCardTime(
    modifier: Modifier = Modifier,
    time: Instant
) {
    Text(
        modifier = modifier
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 12.dp, vertical = 1.dp),
        text = time.toStringRelative()
    )
}


@Composable
fun FromGroupText(
    groupName: String,
    groupColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val style = MaterialTheme.typography.bodySmall
        Text(
            text = "от", // TODO: res
            style = style
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(14.dp)
                    .background(groupColor)
            )
            Text(
                text = groupName,
                fontStyle = style.fontStyle,
                fontSize = style.fontSize,
                fontWeight = style.fontWeight
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupEventCardPreview() {
    val data = Event(
        name = "Fix bugs"
    )
    GroupEventerTheme {
        Box(
            modifier = Modifier
                .size(width = 400.dp, height = 900.dp)
                .padding(16.dp)
        )
        {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(5)
                {
                    EventCard(
                        pair = data to Group(name = "ded"),
                        modifier = Modifier.fillMaxWidth(),
                        onOptionClicked = {}
                    )
                }
            }
        }
    }
}