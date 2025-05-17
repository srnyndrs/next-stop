package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.shared.domain.model.single.TripPlan

@Composable
fun TripPlanContent(
    modifier: Modifier = Modifier,
    tripPlan: TripPlan
) {
    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tripPlan.points.forEach { tripPoint ->
            TripPointRow(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                tripPoint = tripPoint
            )
        }
    }
}