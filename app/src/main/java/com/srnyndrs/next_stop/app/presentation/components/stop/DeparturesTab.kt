package com.srnyndrs.next_stop.app.presentation.components.stop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.components.departure.DepartureItem
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails

@Composable
fun DeparturesTab(
    modifier: Modifier = Modifier,
    stopDetails: StopDetails,
    onTripSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = stopDetails.departures, key = { it.tripId }) { departure ->
                DepartureItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onTripSelect(departure.tripId)
                        },
                    departure = departure,
                    trip = stopDetails.trips[departure.tripId]!!,
                    route = stopDetails.routes[departure.routeId]!!
                )
            }
        }
    }
}