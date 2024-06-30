package com.lumen1024.groupeventer.entities.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.lumen1024.groupeventer.entities.group.model.Group
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme
import kotlin.random.Random

@Composable
fun GroupCard(
    group: Group,
    modifier: Modifier = Modifier,
    hidden: Boolean = false,
    onHide: () -> Unit = {}
) {

    Card(modifier = modifier.height(IntrinsicSize.Min)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(12.dp)
                    .background(Color(group.color.toColorInt()))
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = group.name
                )
                Row(
                    modifier = Modifier.height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                )
                {
                    Text(text = group.people.size.toString())
                    Icon(imageVector = Icons.Default.Person, contentDescription = "")
                    IconButton(onClick = { onHide() }) {
                        Icon(
                            imageVector = if (hidden)
                                Icons.Default.VisibilityOff
                            else
                                Icons.Default.Visibility,
                            contentDescription = "hide icon"
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {



            }


        }

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
                GroupCard(
                    group = Group(
                        name = "Dota 2",
                        color = randomColor(),
                        people = listOf("ded", "ded")
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    hidden = Random.nextBoolean()
                )
            }
        }
    }

}

fun randomColor(alpha: Int = 255) =
    "#" +
    Color(
    Random.nextInt(256),
    Random.nextInt(256),
    Random.nextInt(256),
    alpha = alpha
).value.toString(16).substring(2,8)