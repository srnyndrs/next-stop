package com.srnyndrs.next_stop.app.presentation.screen_map.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.app.presentation.components.departure.DepartureItem
import com.srnyndrs.next_stop.app.presentation.screen_map.GoogleMapState

@Composable
fun DeparturesTab(
    modifier: Modifier = Modifier,
    googleMapState: State<GoogleMapState>,
    onTripSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        googleMapState.value.selectedStop?.let { selectedStop ->
            if (googleMapState.value.isLoading) {
                LinearProgressIndicator()
            }

            if (googleMapState.value.isError) {
                Text(
                    text = "Error: ${googleMapState.value.error?.message}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            when (val stopDetailsState = googleMapState.value.stopDetails) {
                is UiState.Empty -> {}
                is UiState.Error -> {
                    val error = stopDetailsState.message!!
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is UiState.Loading -> {
                    LinearProgressIndicator()
                }
                is UiState.Success -> {
                    val stopDetails = stopDetailsState.data!!

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
        }
    }
}