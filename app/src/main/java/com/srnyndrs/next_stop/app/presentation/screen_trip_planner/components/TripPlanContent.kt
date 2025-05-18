package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.util.toMinutes
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.TripPlan

@Composable
fun TripPlanContent(
    modifier: Modifier = Modifier,
    tripPlan: TripPlan,
    routes: Map<String, Route>
) {
    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Duration
        Column(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = tripPlan.duration.toMinutes(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${tripPlan.startTime} - ${tripPlan.endTime}",
                style = MaterialTheme.typography.titleMedium
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(tripPlan.points) { index, tripPoint ->
                val route = tripPoint.routeId?.let { routes[it] }
                TripPointRow(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    tripPoint = tripPoint,
                    route = route
                )
                if(index < tripPlan.points.lastIndex) {
                    Column (
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
                            )
                        }
                    }
                }
            }
        }
    }
}