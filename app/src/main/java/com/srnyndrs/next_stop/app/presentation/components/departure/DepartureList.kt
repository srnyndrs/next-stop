package com.srnyndrs.next_stop.app.presentation.components.departure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.components.stop.StopHeader
import com.srnyndrs.next_stop.app.presentation.sample.NearbyDeparturesPreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures

@Composable
fun DepartureList(
    modifier: Modifier = Modifier,
    nearbyDepartures: NearbyDepartures,
    onItemSelect: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(nearbyDepartures.departures.toList()) { (stopId, departures) ->
            nearbyDepartures.stops[stopId]?.let { stop ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Stop
                    StopHeader(
                        modifier = Modifier.fillMaxWidth(),
                        stop = stop
                    )
                    // Divider
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 1.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    // Departures
                    departures.forEach { departureEntry ->
                        nearbyDepartures.trips[departureEntry.tripId]?.let { trip ->
                            nearbyDepartures.routes[departureEntry.routeId]?.let { route ->
                                DepartureItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onItemSelect(departureEntry.tripId) }
                                        .padding(horizontal = 4.dp),
                                    departure = departureEntry,
                                    trip = trip,
                                    route = route
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun DepartureListPreview(
    @PreviewParameter(NearbyDeparturesPreviewParameterProvider::class) nearbyDepartures: NearbyDepartures
) {
    NextStopTheme {
        Surface {
            DepartureList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                nearbyDepartures = nearbyDepartures,
                onItemSelect = {}
            )
        }
    }
}