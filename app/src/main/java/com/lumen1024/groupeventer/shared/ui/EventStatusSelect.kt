package com.lumen1024.groupeventer.shared.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumen1024.groupeventer.entities.group_event.model.GroupEventStatus
import com.lumen1024.groupeventer.entities.group_event.model.getColorResource
import com.lumen1024.groupeventer.entities.group_event.model.getIcon
import com.lumen1024.groupeventer.shared.model.GroupEventerTheme

@Composable
fun EventStatusSelect(
    modifier: Modifier = Modifier,
    selected: GroupEventStatus = GroupEventStatus.Prepare,
    onItemSelected: (GroupEventStatus) -> Unit
) {
    val cornersRadius = 16.dp
    val startShape = RoundedCornerShape(topStart = cornersRadius, bottomStart = cornersRadius)
    val midShape = RectangleShape
    val endShape = RoundedCornerShape(topEnd = cornersRadius, bottomEnd = cornersRadius)
    Box(modifier = modifier)
    {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(cornersRadius))
                .height(intrinsicSize = IntrinsicSize.Min)
                .width(360.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    RoundedCornerShape(cornersRadius)
                )

        ) {
            val items = GroupEventStatus.entries.toList()

            items.forEachIndexed { index, status ->
                val shape = when (status) {
                    GroupEventStatus.Prepare -> startShape
                    GroupEventStatus.Voting -> midShape
                    GroupEventStatus.Ended -> endShape
                }
                StatusSelectItem(
                    status = status,
                    shape = shape,
                    onSelected = onItemSelected,
                    selected = selected
                )
                if (index != 2) VerticalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    }

}

@Composable
fun RowScope.StatusSelectItem(
    status: GroupEventStatus,
    shape: Shape,
    onSelected: (GroupEventStatus) -> Unit,
    selected: GroupEventStatus
) {
    val backgroundColor = colorResource(status.getColorResource())
    val weight: Float by animateFloatAsState(
        if (selected == status) 1.25f else 1f,
        label = "weight"
    )
    val color: Color by animateColorAsState(
        if (status == selected) backgroundColor else Color.Transparent,
        label = "color"
    )
    Button(
        modifier = Modifier
            .fillMaxHeight()
            .weight(weight),
        shape = shape,
        onClick = { onSelected(status) },
        colors = ButtonDefaults.buttonColors(

            containerColor = color
        ),
    ) {
        if (selected == status) {
            Text(
                text = status.name,
                color = contentColorFor(backgroundColor)
            )
        } else {
            Icon(
                imageVector = status.getIcon(),
                tint = contentColorFor(MaterialTheme.colorScheme.surface),
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventStatusSelectPreview() {
    GroupEventerTheme {
        var selected by remember { mutableStateOf(GroupEventStatus.Prepare) }
        EventStatusSelect(selected = selected) {
            selected = it
        }
    }

}