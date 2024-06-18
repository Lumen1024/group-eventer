package com.lumen1024.groupeventer.screen.home.events

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumen1024.groupeventer.data.events.GroupEvent

@Composable
fun GroupEventCard(event: GroupEvent, modifier: Modifier = Modifier) {

    Card(modifier = modifier) {
        Column(modifier = modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Face, contentDescription = "", modifier = modifier.size(24.dp))
                    Text(text = "Михаил", modifier = Modifier.padding(horizontal = 5.dp))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "", modifier = modifier.size(16.dp))
                    Text(text = "3/5", modifier = Modifier.padding(end = 20.dp))
                    Text(text = "Голосование", modifier = Modifier.padding(end = 5.dp))
                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(
                            color = Color(0xFF4ED239),
                            radius = 8.dp.toPx(),
                            center = center,
                            style = Fill
                        )
                    }
                }
            }
            Text(text = event.name, modifier = Modifier.padding(vertical = 20.dp), fontSize = 20.sp, color = Color.Black)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "22 июня 18:00 - 23 июня 03:00")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, "", modifier = modifier.size(16.dp))
                    Text(text = "3 ч", modifier = Modifier.padding(horizontal = 5.dp))
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