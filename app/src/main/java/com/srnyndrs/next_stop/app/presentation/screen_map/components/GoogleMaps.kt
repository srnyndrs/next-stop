package com.srnyndrs.next_stop.app.presentation.screen_map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.LocateFixed
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Minus
import com.composables.icons.lucide.Plus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.srnyndrs.next_stop.app.presentation.components.maps.GoogleMapContainer
import com.srnyndrs.next_stop.app.presentation.components.stop.marker.MarkerContent
import com.srnyndrs.next_stop.app.presentation.screen_map.GoogleMapState
import com.srnyndrs.next_stop.app.presentation.screen_map.MapScreenEvent
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude
import kotlinx.coroutines.launch

@Composable
fun GoogleMaps(
    modifier: Modifier = Modifier,
    googleMapState: State<GoogleMapState>,
    cameraPositionState: CameraPositionState,
    onEvent: (MapScreenEvent) -> Unit
) {

    val scope = rememberCoroutineScope()
    val state = googleMapState.value

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            if(cameraPositionState.position.zoom >= 16f) {
                cameraPositionState.projection?.let {
                    onEvent(MapScreenEvent.MapBoundsChangeEvent(bounds = it.visibleRegion.latLngBounds))
                }
            }
        }
    }

    Box(
        modifier = Modifier.then(modifier),
    ) {
        GoogleMapContainer(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                onEvent(MapScreenEvent.MapClickEvent)
            }
        ) {
            if (cameraPositionState.position.zoom >= 16f) {
                state.stops.forEach { stop ->
                    stop.location?.let { stopLocation ->
                        val selected = state.selectedStop == stop
                        val colors = stop.colors
                        val markerState = MarkerState(
                            position = stopLocation.let {
                                LatLng(
                                    it.latitude(),
                                    it.longitude()
                                )
                            }
                        )
                        MarkerComposable(
                            state = markerState,
                            title = stop.stopName,
                            anchor = Offset(0.5f, 0.5f),
                            keys = arrayOf(selected, colors, stop),
                            onClick = {
                                onEvent(MapScreenEvent.MarkerClickEvent(stop))
                                true
                            }
                        ) {
                            MarkerContent(
                                selected = selected,
                                colors = colors,
                                rotationDegree = stop.direction
                            )
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .requiredWidth(48.dp)
                .align(Alignment.BottomEnd)
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(5.dp)),
                onClick = {
                    scope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                googleMapState.value.cameraPosition.target,
                                16f
                            )
                        )
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    imageVector = Lucide.LocateFixed,
                    contentDescription = "User Location"
                )
            }
            // Zoom options
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    onClick = {
                        scope.launch {
                            cameraPositionState.animate(CameraUpdateFactory.zoomBy(1f))
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Lucide.Plus,
                        contentDescription = "Zoom In"
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    onClick = {
                        scope.launch {
                            cameraPositionState.animate(CameraUpdateFactory.zoomBy(-1f))
                        }
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Lucide.Minus,
                        contentDescription = "Zoom Out"
                    )
                }
            }
        }
    }
}