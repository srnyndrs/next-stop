package com.srnyndrs.next_stop.app.presentation.components.trip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.CircleAlert
import com.composables.icons.lucide.Lucide
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.screen_trip_details.TripDetailsScreenEvent
import com.srnyndrs.next_stop.app.presentation.sample.TripDetailsPreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.toPainterResource
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails

@Composable
fun TripDetails(
    modifier: Modifier = Modifier,
    tripDetails: TripDetails,
    onEvent: (TripDetailsScreenEvent) -> Unit,
    onBack: () -> Unit
) {

    val currentStopSequence = tripDetails.currentStopSequence ?: 0
    val lazyColumnState = rememberLazyListState()
    var favouriteState by rememberSaveable { mutableStateOf(tripDetails.favourite) }

    LaunchedEffect(Unit) {
        if(currentStopSequence > 0) {
            lazyColumnState.animateScrollToItem(
                index = currentStopSequence - 1
            )
        }
    }

    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Top
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Route information
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(0.08f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Lucide.ArrowLeft,
                            contentDescription = null
                        )
                    }
                }
                Row(
                    modifier = Modifier.weight(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Route title
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RouteIcon(
                            route = tripDetails.route,
                            alertActive = tripDetails.alerts.isNotEmpty(),
                            wheelchairAccessible = tripDetails.trip.wheelchairAccessible
                        )
                        Text(
                            text = tripDetails.trip.tripHeadsign,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    // Favourite
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            // Change state
                            favouriteState = !favouriteState

                            // Send event according to new value
                            if(favouriteState) {
                                onEvent(
                                    TripDetailsScreenEvent.MarkRouteAsFavourite(
                                        route = tripDetails.route
                                    )
                                )
                            } else {
                                onEvent(
                                    TripDetailsScreenEvent.RemoveRouteFromFavourites(
                                        routeId = tripDetails.route.routeId
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if(favouriteState) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if(favouriteState) Color.Red else LocalContentColor.current
                        )
                    }
                }
            }
            // Divider
            HorizontalDivider(
                modifier = Modifier.clip(RoundedCornerShape(5.dp)),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        // Alerts
        if(tripDetails.alerts.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tripDetails.alerts.forEach { alert ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(72.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxHeight(),
                                imageVector = Lucide.CircleAlert,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = alert.description,
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3
                            )
                        }
                    }
                }
            }
        }
        // Stops
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            state = lazyColumnState
        ) {
            // Stops
            itemsIndexed(tripDetails.schedule.toList()) { index, (stop, time) ->
                val stopStatus = when(index + 1) {
                    in 1..<currentStopSequence -> StopStatus.DEPARTED
                    currentStopSequence -> StopStatus.CURRENT
                    else -> StopStatus.UPCOMING
                }

                val color = when (stopStatus) {
                    StopStatus.DEPARTED -> Color.DarkGray
                    StopStatus.UPCOMING -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(42.dp)
                        .padding(horizontal = 3.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(0.1f),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(color, CircleShape)
                            ) {
                                if(stopStatus == StopStatus.CURRENT) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize(1f)
                                            .align(Alignment.Center),
                                        painter = tripDetails.route.routeType.toPainterResource(),
                                        contentDescription = "${tripDetails.route.routeType.name} icon"
                                    )
                                }
                            }
                            // Stop Divider
                            if(index < tripDetails.schedule.toList().lastIndex) {
                                VerticalDivider(
                                    thickness = 4.dp,
                                    color = color
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.weight(0.9f).padding(top = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stop.stopName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if(stopStatus == StopStatus.CURRENT) FontWeight.SemiBold else FontWeight.Normal,
                            color = color,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = time,
                            color = color,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if(stopStatus == StopStatus.CURRENT) FontWeight.SemiBold else FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

enum class StopStatus {
    DEPARTED,
    CURRENT,
    UPCOMING
}

@PreviewLightDark
@Composable
fun TripDetailsPreview(
    @PreviewParameter(TripDetailsPreviewParameterProvider::class) tripDetails: TripDetails
) {
    NextStopTheme {
        Surface {
            TripDetails(
                modifier = Modifier.fillMaxWidth().padding(6.dp),
                tripDetails = tripDetails,
                onEvent = {},
                onBack = {}
            )
        }
    }
}