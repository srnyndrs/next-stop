package com.srnyndrs.next_stop.app.presentation.components.stop.marker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme

@Composable
fun TriangleShape(
    modifier: Modifier = Modifier,
    color: Color,
    diameter: Dp,
    rotationDegree: Float
) {

    val trianglePath = remember {
        Path()
    }

    val triangleWidth = 8.dp
    val triangleHeight = 12.dp
    val offsetX = 8.dp

    Canvas(
        modifier = Modifier.then(modifier)
    ) {

        val radius = diameter.toPx() / 2
        val centerX = center.x
        val centerY = center.y
        val triangleWidthPx = triangleWidth.toPx()
        val triangleHeightPx = triangleHeight.toPx()
        val diff = offsetX.toPx()

        val triangleStartX = centerX + radius - diff

        if (trianglePath.isEmpty) {
            trianglePath.apply {
                moveTo(triangleStartX, centerY - triangleHeightPx / 2)
                lineTo(triangleStartX + triangleWidthPx, centerY)
                lineTo(triangleStartX, centerY + +triangleHeightPx / 2)
                lineTo(triangleStartX, centerY - triangleHeightPx / 2)
            }
        }

        rotate(rotationDegree - 90f) {
            drawPath(trianglePath, color)
        }
    }
}

@Preview
@Composable
private fun CanvasRotationSample() {
    NextStopTheme {
        TriangleShape(
            modifier = Modifier.size(24.dp),
            color = Color.White,
            diameter = 24.dp,
            rotationDegree = 90F
        )
    }
}