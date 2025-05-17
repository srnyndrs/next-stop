package com.srnyndrs.next_stop.app.presentation.screen_map.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.fromHex

@Composable
fun MarkerContent(
    selected: Boolean,
    colors: List<String>,
    rotationDegree: Float = 0F
) {

    val style = colors.map { Color.fromHex(it) }

    val size: Dp by animateDpAsState(
        targetValue = if(selected) 32.dp else 28.dp
    )

    Box(
        modifier = Modifier
            .size(size)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        TriangleShape(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onSurface, //Color.White,
            diameter = size,
            rotationDegree = rotationDegree
        )
        Box(
            modifier = Modifier
                .fillMaxSize(0.7f)
                .align(Alignment.Center)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.7f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            MultiColorPieSlice(
                modifier = Modifier.fillMaxSize(),
                colors = style
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(0.7f)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun CustomMapMarkerPreview() {
    NextStopTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(56.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(2) { n ->
                var selected by remember {
                    mutableStateOf(n % 2 == 0)
                }
                Box(
                    modifier = Modifier.clickable {
                        selected = !selected
                    }
                ) {
                    MarkerContent(
                        selected = selected,
                        colors = listOf(
                            "FFD800",
                            "E41F18",
                            "000000",
                            "009EE3"
                        ),
                        rotationDegree = 90f
                    )
                }
            }
        }
    }
}