package com.srnyndrs.next_stop.app.presentation.screen_trip_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.trip.TripDetails

@Composable
fun TripDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: TripDetailsViewModel,
    navController: NavController
) {

    val tripDetailsState by viewModel.tripDetails.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UiStateContainer(
            modifier = Modifier.fillMaxSize(),
            uiState = tripDetailsState
        ) { tripDetails ->
            TripDetails(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                tripDetails = tripDetails,
                onEvent = { event ->
                    viewModel.onEvent(event)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}