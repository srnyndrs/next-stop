package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.srnyndrs.next_stop.shared.domain.model.single.TripPoint

@Composable
fun TripPointRow(
    modifier: Modifier = Modifier,
    tripPoint: TripPoint
) {
    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = tripPoint.name)
        Text(text = tripPoint.time)
    }
}