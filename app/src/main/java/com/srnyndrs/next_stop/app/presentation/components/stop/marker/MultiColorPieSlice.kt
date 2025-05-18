package com.srnyndrs.next_stop.app.presentation.components.stop.marker

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MultiColorPieSlice(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    startAngle: Float = 0f,
    sweepAngle: Float = 360f
) {
    Canvas(
        modifier = Modifier.then(modifier)
    ) {
        val sliceCount = colors.size
        val sliceAngle = sweepAngle / sliceCount

        colors.forEachIndexed { index, color ->
            drawArc(
                color = color,
                startAngle = startAngle + index * sliceAngle,
                sweepAngle = sliceAngle,
                useCenter = true
            )
        }
    }
}