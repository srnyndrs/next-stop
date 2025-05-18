package com.srnyndrs.next_stop.app.presentation.components.stop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.srnyndrs.next_stop.app.presentation.components.TabRowComponent
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.navigation.Screen
import com.srnyndrs.next_stop.app.presentation.screen_map.components.DeparturesTab
import com.srnyndrs.next_stop.app.presentation.screen_map.components.ScheduleTab

@Composable
fun StopDetails(
    modifier: Modifier = Modifier
) {

    // Tabs
    TabRowComponent(
        modifier = Modifier.then(modifier),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tabs = listOf("Departures", "Schedule"),
        contentScreens = listOf (
            {
                DeparturesTab(
                    modifier = Modifier.fillMaxSize(),
                    googleMapState = googleMapState,
                    onTripSelect = { tripId ->
                        navController.navigate(Screen.TripDetailsScreen.passTripId(tripId))
                    }
                )
            },
            {
                UiStateContainer(
                    modifier = Modifier.fillMaxSize(),
                    uiState = googleMapState.value.stopSchedule
                ) { data ->
                    ScheduleTab(
                        modifier = Modifier.fillMaxSize(),
                        stopSchedule = data,
                        onTripSelect = { tripId ->
                            navController.navigate(Screen.TripDetailsScreen.passTripId(tripId))
                        }
                    )
                }
            }
        )
    )

}