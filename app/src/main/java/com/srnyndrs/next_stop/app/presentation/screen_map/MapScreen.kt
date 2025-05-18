package com.srnyndrs.next_stop.app.presentation.screen_map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pen
import com.composables.icons.lucide.Plus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.srnyndrs.next_stop.app.presentation.components.TabRowComponent
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.search.SearchBar
import com.srnyndrs.next_stop.app.presentation.navigation.Screen
import com.srnyndrs.next_stop.app.presentation.screen_map.components.DeparturesTab
import com.srnyndrs.next_stop.app.presentation.screen_map.components.GoogleMaps
import com.srnyndrs.next_stop.app.presentation.screen_map.components.ScheduleTab
import com.srnyndrs.next_stop.app.presentation.components.search.SearchViewModel
import com.srnyndrs.next_stop.app.presentation.components.stop.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
    searchViewModel: SearchViewModel,
    navController: NavController,
    onEvent: (MapScreenEvent) -> Unit
) {

    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        confirmValueChange = { targetValue ->
            if(targetValue == SheetValue.Hidden) {
                onEvent(MapScreenEvent.MapClickEvent)
            }
            true
        },
        skipHiddenState = false
    )
    val scaffoldSheetState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val googleMapState = viewModel.state.collectAsStateWithLifecycle()

    val results by searchViewModel.results.collectAsStateWithLifecycle()

    val cameraPositionState by remember {
        mutableStateOf(CameraPositionState.invoke(position = googleMapState.value.cameraPosition))
    }

    LaunchedEffect(scaffoldSheetState.bottomSheetState.targetValue) {
        if(scaffoldSheetState.bottomSheetState.targetValue == SheetValue.Expanded) {
            googleMapState.value.selectedStop?.let { stop ->
                onEvent(MapScreenEvent.GetStopDetailsEvent(stop.stopId))
            }
        }
    }

    LaunchedEffect(googleMapState.value.selectedStop) {
        googleMapState.value.selectedStop?.let {
            bottomSheetState.partialExpand()
        } ?: bottomSheetState.hide()
    }

    BottomSheetScaffold(
        modifier = Modifier.then(modifier),
        scaffoldState = scaffoldSheetState,
        sheetPeekHeight = 92.dp,
        //sheetSwipeEnabled = bottomSheetState.currentValue != SheetValue.Expanded,
        sheetShape = when(scaffoldSheetState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> RectangleShape
            else -> BottomSheetDefaults.ExpandedShape
        },
        /*sheetDragHandle = {
            AnimatedVisibility(
                visible = (scaffoldSheetState.bottomSheetState.currentValue != SheetValue.Expanded)
                        || (scaffoldSheetState.bottomSheetState.targetValue != SheetValue.Expanded)
            ) {
                BottomSheetDefaults.DragHandle()
            }
        },*/
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetContent = {
            /*Column(
                modifier = Modifier.fillMaxSize().padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {*/
                // Title
                /*Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if(bottomSheetState.currentValue == SheetValue.Expanded) {
                        Arrangement.SpaceBetween
                    } else {
                        Arrangement.Center
                    }
                ) {
                    googleMapState.value.selectedStop?.stopName?.let { stopName ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stopName,
                                style = MaterialTheme.typography.titleLarge
                            )
                            IconButton(
                                modifier = Modifier.size(32.dp),
                                onClick = {
                                    val destination = googleMapState.value.selectedStop?.location
                                    destination?.let { (lat, lon) ->
                                        navController.navigate(Screen.TripPlannerScreen.passDestination("$lat,$lon"))
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Lucide.Pen,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = bottomSheetState.currentValue == SheetValue.Expanded
                    ) {
                        IconButton(
                            modifier = Modifier.size(32.dp),
                            onClick = {
                                scope.launch {
                                    bottomSheetState.partialExpand()
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier.rotate(45f),
                                imageVector = Lucide.Plus,
                                contentDescription = "Close window" // TODO
                            )
                        }
                    }
                }*/
                // Content
                /*AnimatedVisibility(
                    visible = bottomSheetState.currentValue == SheetValue.Expanded
                ) {*/
                    StopDetails(
                        modifier = Modifier.fillMaxSize(),
                        stopName = googleMapState.value.selectedStop?.stopName ?: "Selected stop",
                        stopDetails = googleMapState.value.stopDetails,
                        stopSchedule = googleMapState.value.stopSchedule,
                        onStopClick = {
                            val stopLocation = googleMapState.value.selectedStop?.location
                            stopLocation?.let {  (lat, lon) ->
                                navController.navigate(Screen.TripPlannerScreen.passDestination("$lat,$lon"))
                            }
                        }
                    ) { tripId ->
                        navController.navigate(Screen.TripDetailsScreen.passTripId(tripId))
                    }
                //}
            //}
        }
    ) { paddingValues ->

        val bottomPadding: Dp by animateDpAsState(
            targetValue = if (bottomSheetState.targetValue == SheetValue.Hidden) {
                0.dp
            } else {
                paddingValues.calculateBottomPadding()
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = bottomPadding
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            GoogleMaps(
                modifier = Modifier.fillMaxSize(),
                googleMapState = googleMapState,
                cameraPositionState = cameraPositionState,
                onEvent = onEvent
            )
            //
            Column(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .padding(vertical = 3.dp, horizontal = 6.dp)
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    placeholderText = "Search stops",
                    searchResults = results,
                    onItemClick = { result ->
                        val stopResult = result as SearchResult.StopResult
                        val location = stopResult.stop.location
                        location?.let { (lat, lon) ->
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(lat, lon),
                                        16f
                                    )
                                )
                                onEvent(MapScreenEvent.MarkerClickEvent(stopResult.stop))
                            }
                        }
                    }
                ) { text ->
                    searchViewModel.onSearchTextChange(text)
                }
            }
        }
    }
}