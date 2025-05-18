package com.srnyndrs.next_stop.app.presentation.components.departure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.sample.NearbyDeparturesPreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.single.Departure
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Trip
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures

@Composable
fun DepartureItem(
    modifier: Modifier = Modifier,
    departure: Departure,
    trip: Trip,
    route: Route
) {
    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(0.85f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // Route Icon
            RouteIcon(
                route = route,
                wheelchairAccessible = departure.wheelchairAccessible,
                alertActive = departure.alertIds.isNotEmpty()
            )
            // Divider
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null
            )
            // Headsign
            Text(
                text = trip.tripHeadsign,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier.weight(0.15f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            // Arrival Time
            Text(
                text = departure.arrivalTime,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@PreviewLightDark
@Composable
fun DepartureItemPreview(
    @PreviewParameter(NearbyDeparturesPreviewParameterProvider::class) nearbyDepartures: NearbyDepartures
) {

    val departure = nearbyDepartures.departures.values.first().first()
    val route = nearbyDepartures.routes[departure.routeId]!!
    val trip = nearbyDepartures.trips[departure.tripId]!!

    NextStopTheme {
        Surface {
            DepartureItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 3.dp),
                departure = departure,
                route = route,
                trip = trip
            )
        }
    }
}