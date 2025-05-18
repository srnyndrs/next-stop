package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.CircleDot
import com.composables.icons.lucide.FlagTriangleRight
import com.composables.icons.lucide.Lucide
import com.srnyndrs.next_stop.app.R
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.sample.RoutePreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.TripPoint

@Composable
fun TripPointRow(
    modifier: Modifier = Modifier,
    tripPoint: TripPoint,
    route: Route? = null
) {
    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when(tripPoint.type) {
                "route" -> {
                    route?.let {
                        RouteIcon(
                            route = it,
                        )
                    }
                }
                "destination" -> {
                    Icon(
                        imageVector = Lucide.FlagTriangleRight,
                        contentDescription = null
                    )
                }
                "walk" -> {
                    Icon(
                        painter = painterResource(R.drawable.walk),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                else -> {
                    Icon(
                        imageVector = Lucide.CircleDot,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = tripPoint.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(text = tripPoint.time)
    }
}

@PreviewLightDark
@Composable
fun TripPointRowPreview(
    @PreviewParameter(RoutePreviewParameterProvider::class) route: Route
) {
    NextStopTheme {
        Surface {
            TripPointRow(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
                tripPoint = TripPoint(
                    type = "route",
                    name = "Origin",
                    time = "13:00"
                ),
                route = route
            )
        }
    }
}