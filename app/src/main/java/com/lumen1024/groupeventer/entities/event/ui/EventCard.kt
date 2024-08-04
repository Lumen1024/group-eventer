package com.lumen1024.groupeventer.entities.event.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.entities.event.model.Event
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import kotlin.time.DurationUnit

@Composable
fun EventCard(
    modifier: Modifier = Modifier,
    pair: Pair<Event, Group>,
    onClick: () -> Unit = {},
    onOptionClicked: () -> Unit = {},
) {
    val event by remember { derivedStateOf { pair.first } }
    val group by remember { derivedStateOf { pair.second } }

    Card(
        modifier = Modifier
            .then(modifier),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        onClick = onClick,
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
                                    .background(group.color.color)
                            )
                            Text(
                                text = group.name,
                                fontStyle = style.fontStyle,
                                fontSize = style.fontSize,
                                fontWeight = style.fontWeight
                            )

                        }
                    }
                    val titleStyle = MaterialTheme.typography.titleLarge
                    Text(
                        text = event.name,
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
                Row(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = "22 июня 18:00") // TODO: insert
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = event.duration.toString(DurationUnit.HOURS)) // TODO: duration parse
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

@Preview(showBackground = true)
@Composable
fun GroupEventCardPreview() {
    val data = Event(

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
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }


    }

}