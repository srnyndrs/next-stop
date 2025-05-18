package com.srnyndrs.next_stop.app.presentation.screen_route_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberMarkerState
import com.srnyndrs.next_stop.app.presentation.common.LoadingType
import com.srnyndrs.next_stop.app.presentation.components.GoogleMapContainer
import com.srnyndrs.next_stop.app.presentation.components.TabRowComponent
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.components.stop.StopDetails
import com.srnyndrs.next_stop.app.presentation.navigation.Screen
import com.srnyndrs.next_stop.app.presentation.screen_map.MapScreenEvent
import com.srnyndrs.next_stop.app.presentation.screen_map.components.MarkerContent
import com.srnyndrs.next_stop.app.presentation.util.fromHex
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RouteDetailsViewModel,
    navController: NavController
) {

    val routeDetailsState by viewModel.routeDetails.collectAsStateWithLifecycle()
    val cameraPositionState = remember {
        CameraPositionState(position = CameraPosition.fromLatLngZoom(LatLng(47.497913, 19.040236), 8f))
    }

    var selectedStop by remember { mutableStateOf<Stop?>(null) }
    val stopDetailsState by viewModel.stopDetails.collectAsStateWithLifecycle()

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        confirmValueChange = { targetValue ->
            if(targetValue == SheetValue.Hidden) {
                selectedStop = null
            }
            true
        },
        skipHiddenState = false
    )
    val scaffoldSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    LaunchedEffect(selectedStop) {
        selectedStop?.let { stop ->
            viewModel.fetchStopDetails(stop.stopId)
            bottomSheetState.partialExpand()
        } ?: bottomSheetState.hide()
    }

    BottomSheetScaffold(
        modifier = Modifier.then(modifier),
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = 92.dp,
        sheetShape = when(scaffoldSheetState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> RectangleShape
            else -> BottomSheetDefaults.ExpandedShape
        },
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            selectedStop?.let { stop ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    /*Text(
                        text = stop.stopName,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )*/
                    StopDetails(
                        modifier = Modifier.fillMaxSize(),
                        stopName = stop.stopName,
                        stopDetails = stopDetailsState.stopDetails,
                        stopSchedule = stopDetailsState.stopSchedule,
                        onStopClick = {
                            val stopLocation = stop.location
                            stopLocation?.let { (lat,lon) ->
                                navController.navigate(Screen.TripPlannerScreen.passDestination("$lat,$lon"))
                            }
                        }
                    ) { tripId ->
                        navController.navigate(Screen.TripDetailsScreen.passTripId(tripId))
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Route Details",
                style = MaterialTheme.typography.titleLarge
            )
            UiStateContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = routeDetailsState,
                loadingType = LoadingType.LINEAR
            ) { routeDetails ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val backgroundColor = routeDetails.route.backgroundColor

                    routeDetails.description?.let {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            RouteIcon(route = routeDetails.route)
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    TabRowComponent(
                        modifier = Modifier.fillMaxSize(),
                        tabs = routeDetails.variants.map { it.headsign },
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        contentScreens = routeDetails.variants.map { routeVariant ->
                            {
                                GoogleMapContainer(
                                    modifier = Modifier.fillMaxSize(),
                                    cameraPositionState = cameraPositionState,
                                    onMapClick = {}
                                ) {
                                    Polyline(
                                        points = routeVariant.routePoints?.map { LatLng(it.latitude(), it.longitude()) } ?: emptyList(),
                                        color = Color.fromHex(backgroundColor),
                                        width = 12f
                                    )
                                    routeVariant.stopIds.forEach { stopId ->
                                        routeDetails.stops[stopId]?.let { stop ->
                                            val colors = stop.colors
                                            val markerState = MarkerState(
                                                position = stop.location!!.let {
                                                    LatLng(
                                                        it.latitude(),
                                                        it.longitude()
                                                    )
                                                }
                                            )
                                            MarkerComposable(
                                                state = markerState,
                                                title = stop.stopName,
                                                keys = arrayOf(colors, stop),
                                                anchor = Offset(0.5f, 0.5f),
                                                onClick = {
                                                    selectedStop = stop
                                                    true
                                                }
                                            ) {
                                                MarkerContent(
                                                    selected = selectedStop == stop,
                                                    colors = colors,
                                                    rotationDegree = stop.direction
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        indicatorColor = Color.fromHex(backgroundColor),
                        onTabChange = {
                            selectedStop = null
                        }
                    )
                }
            }
        }
    }
}