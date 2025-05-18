package com.srnyndrs.next_stop.app.presentation.screen_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.components.departure.DepartureList
import com.srnyndrs.next_stop.app.presentation.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navController: NavController,
    onEvent: (HomeScreenEvent) -> Unit
) {

    val departureListState by viewModel.departures.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Nearby Departures",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    onEvent(HomeScreenEvent.GetNearbyDeparturesEvent)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh button"
                )
            }
        }
        // Departures
        UiStateContainer(
            modifier = Modifier.fillMaxSize(),
            uiState = departureListState
        ) { data ->
            DepartureList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                nearbyDepartures = data
            ) { tripId ->
                navController.navigate(Screen.TripDetailsScreen.passTripId(tripId))
            }
        }
    }
}