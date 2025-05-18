package com.srnyndrs.next_stop.app.presentation.components.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Accessibility
import com.composables.icons.lucide.CircleAlert
import com.composables.icons.lucide.Lucide
import com.srnyndrs.next_stop.app.presentation.sample.RoutePreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.fromHex
import com.srnyndrs.next_stop.app.presentation.util.toPainterResource
import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.domain.model.single.Route

@Composable
fun RouteIcon(
    modifier: Modifier = Modifier,
    route: Route,
    alertActive: Boolean = false,
    wheelchairAccessible: Boolean = false
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(
            when(route.iconDisplayType) {
                ShapeType.CIRCLE -> 0.dp
                else -> 2.dp
            }
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .requiredHeight(32.dp)
                .requiredWidth(30.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .align(Alignment.CenterStart),
                painter = route.routeType.toPainterResource(),
                contentDescription = "${route.routeType.name} icon"
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.TopEnd)
            ) {
                // Alert
                if(alertActive) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(12.dp)
                            .background(Color.Red, CircleShape),
                        imageVector = Lucide.CircleAlert,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                // Wheelchair
                if(wheelchairAccessible) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(12.dp)
                            .background(Color.Blue, CircleShape)
                            .padding(1.dp),
                        imageVector = Lucide.Accessibility,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .requiredHeight(24.dp)
                .requiredWidth(
                    if (route.iconDisplayType == ShapeType.BOX) {
                        42.dp
                    } else 24.dp
                )
                .clip(
                    when(route.iconDisplayType) {
                        ShapeType.CIRCLE -> CircleShape
                        else -> RoundedCornerShape(4.dp)
                    }
                )
                .background(Color.fromHex(route.backgroundColor))
                .padding(3.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                modifier = Modifier.requiredHeight(24.dp),
                text = route.routeName,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.fromHex(route.textColor)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun RouteIconPreview(
    @PreviewParameter(RoutePreviewParameterProvider::class) route: Route
) {
    NextStopTheme {
        Surface {
            RouteIcon(
                modifier = Modifier.padding(6.dp),
                route = route
            )
        }
    }
}