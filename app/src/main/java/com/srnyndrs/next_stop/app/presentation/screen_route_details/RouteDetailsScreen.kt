package com.srnyndrs.next_stop.app.presentation.screen_route_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.srnyndrs.next_stop.app.presentation.common.LoadingType
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.screen_map.MapScreenEvent
import com.srnyndrs.next_stop.app.presentation.screen_map.components.MarkerContent
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude

@Composable
fun RouteDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteDetailsViewModel
) {

    val routeDetailsState by viewModel.routeDetails.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UiStateContainer(
            modifier = Modifier.fillMaxSize(),
            uiState = routeDetailsState,
            loadingType = LoadingType.LINEAR
        ) { routeDetails ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                routeDetails.variants.forEach { routeVariant ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        RouteIcon(
                            route = routeDetails.route
                        )
                        Text(
                            text = routeVariant.headsign
                        )
                    }
                }
                GoogleMap(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Polyline(
                        points = routeDetails.variants[0].routePoints?.map { LatLng(it.latitude(), it.longitude()) } ?: emptyList()
                    )
                    routeDetails.stops.forEach { (_, stop) ->
                        val colors = stop.colors
                        val markerState = MarkerState(
                            position = stop.location.let {
                                LatLng(
                                    it!!.latitude(),
                                    it!!.longitude() // TODO
                                )
                            }
                        )
                        MarkerComposable(
                            state = markerState,
                            title = stop.stopName,
                            keys = arrayOf(colors, stop),
                            onClick = {
                                //onEvent(MapScreenEvent.MarkerClickEvent(stop))
                                true
                            }
                        ) {
                            MarkerContent(
                                selected = false,
                                colors = colors,
                                rotationDegree = stop.direction ?: 0F
                            )
                        }
                    }
                }
            }
        }
    }
}