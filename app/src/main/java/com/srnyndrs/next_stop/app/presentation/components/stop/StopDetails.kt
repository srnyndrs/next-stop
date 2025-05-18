package com.srnyndrs.next_stop.app.presentation.components.stop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ExternalLink
import com.composables.icons.lucide.Lucide
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.app.presentation.components.TabRowComponent
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule

@Composable
fun StopDetails(
    modifier: Modifier = Modifier,
    stopName: String,
    stopDetails: UiState<StopDetails>,
    stopSchedule: UiState<StopSchedule>,
    onStopClick: () -> Unit,
    onTripSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier.then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .clickable { onStopClick() },
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stopName,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Lucide.ExternalLink,
                contentDescription = null
            )
        }
        // Tabs
        TabRowComponent(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tabs = listOf("Departures", "Schedule"),
            contentScreens = listOf (
                {
                    UiStateContainer(
                        modifier = Modifier.fillMaxSize(),
                        uiState = stopDetails
                    ) { data ->
                        DeparturesTab(
                            modifier = Modifier.fillMaxSize(),
                            stopDetails = data,
                            onTripSelect = onTripSelect
                        )
                    }
                },
                {
                    UiStateContainer(
                        modifier = Modifier.fillMaxSize(),
                        uiState = stopSchedule
                    ) { data ->
                        ScheduleTab(
                            modifier = Modifier.fillMaxSize(),
                            stopSchedule = data,
                            onTripSelect = onTripSelect
                        )
                    }
                }
            )
        )
    }
}