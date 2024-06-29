package com.lumen1024.groupeventer.entities.group_event.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumen1024.groupeventer.R
import com.lumen1024.groupeventer.entities.group_event.model.GroupEvent
import com.lumen1024.groupeventer.shared.lib.TimeRangeFormatter
import com.lumen1024.groupeventer.shared.ui.EventStatusIndicator
import java.time.Instant

@Composable
fun GroupEventCard(event: GroupEvent, modifier: Modifier = Modifier) {
    val timeFrom = TimeRangeFormatter.formatDayMonthTime(Instant.parse(event.acceptedRange.start))
    val timeTo = TimeRangeFormatter.formatDayMonthTime(
        Instant.parse(event.acceptedRange.end)
    )
    val duration = pluralStringResource(id = R.plurals.hour, 1)

    Card(modifier = modifier) {
        Column(modifier = modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Face,
                        contentDescription = "",
                        modifier = modifier.size(24.dp)
                    )
                    Text(text = "Михаил", modifier = Modifier.padding(horizontal = 5.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "",
                        modifier = modifier.size(16.dp)
                    )
                    Text(text = "3/5", modifier = Modifier.padding(end = 20.dp))
                    Text(
                        text = "Голосование",
                        modifier = Modifier.padding(end = 5.dp),
                    )
                    EventStatusIndicator(status = event.status)
                }
            }
            Text(
                text = event.name,
                modifier = Modifier.padding(vertical = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$timeFrom - $timeTo"
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, "", modifier = modifier.size(16.dp))
                    Text(text = duration, modifier = Modifier.padding(horizontal = 5.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun GroupEventCardPreview() {
    val data = GroupEvent(

    )
    GroupEventCard(event = data)
}