package com.lumen1024.ui.shared

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.domain.data.Group
import com.lumen1024.domain.data.GroupColor
import com.lumen1024.domain.data.GroupRole
import com.lumen1024.ui.config.GroupEventerTheme
import java.util.UUID

@Composable
fun GroupItem(
    group: Group,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(20.dp)
                    .background(Color(group.color.hex))
            )
            Text(text = group.name)
        }

        Text(
            text = "${group.members.size + 1} people",
            style = MaterialTheme.typography.bodySmall,
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
                .background(Color.Transparent),
        ) {
            repeat(6) {
                GroupItem(
                    group = Group(
                        name = "Dota 2",
                        color = GroupColor.RED,
                        members = mapOf(
                            UUID.randomUUID().toString() to GroupRole.Admin,
                            UUID.randomUUID().toString() to GroupRole.Member
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
                HorizontalDivider()
            }
        }
    }

}
