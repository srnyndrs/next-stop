package com.srnyndrs.next_stop.app.presentation.screen_map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.components.route.RouteIcon
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.ScheduleTime
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule

@Composable
fun ScheduleTab(
    modifier: Modifier = Modifier,
    stopSchedule: StopSchedule,
    onTripSelect: (String) -> Unit
) {

    val scrollState = rememberScrollState()
    var selectedRoutes by remember { mutableStateOf(stopSchedule.routes.keys.toList()) }

    val filteredRoutes = { routes: List<String> ->
        stopSchedule.schedule
            .filter { routes.contains(it.routeId) }
            .sortedBy { it.arrivalTime }
            .groupBy { it.arrivalTime.take(2) }
    }

    val handleSelection = { routeId: String, newValue: Boolean ->
        selectedRoutes = if(newValue) {
            selectedRoutes.toMutableList().apply {
                add(routeId)
            }
        } else {
            selectedRoutes.toMutableList().apply {
                remove(routeId)
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            stopSchedule.routes.forEach { (routeId, route) ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            handleSelection(routeId, !selectedRoutes.contains(routeId))
                        }
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(vertical = 3.dp, horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    RouteIcon(
                        modifier = Modifier,
                        route = route,
                    )
                    Checkbox(
                        modifier = Modifier.size(24.dp),
                        checked = selectedRoutes.contains(routeId),
                        onCheckedChange = { value ->
                            handleSelection(routeId, value)
                        }
                    )
                }
            }
        }
        HorizontalDivider()
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            //val schedules = schedule.schedule.sortedBy { it.arrivalTime }.groupBy { it.arrivalTime.take(2) }
            filteredRoutes(selectedRoutes).forEach { (hour, scheduleTimes) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(0.8f),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            //modifier = Modifier.fillMaxWidth(),
                            text = hour,
                            textAlign = TextAlign.Center
                        )
                        VerticalDivider(
                            modifier = Modifier.requiredHeight(16.dp).align(Alignment.CenterVertically),
                            thickness = 2.dp
                        )
                    }
                    FlowRow(
                        modifier = Modifier.weight(10f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        scheduleTimes.forEach { scheduleTime ->
                            val minute = scheduleTime.arrivalTime.takeLast(2)
                            Column(
                                modifier = Modifier
                                    .requiredWidth(24.dp)
                                    .clickable {
                                        onTripSelect(scheduleTime.tripId)
                                    }
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = minute,
                                    textAlign = TextAlign.Center,
                                    /*color = if(hour <= currentHour && minute < currentMinutes) {
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    } else MaterialTheme.colorScheme.onSurface*/
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ScheduleTabPreview() {
    NextStopTheme {
        Surface {
            ScheduleTab(
                modifier = Modifier.fillMaxSize().padding(6.dp),
                stopSchedule = StopSchedule(
                    stopId = "1",
                    schedule = listOf(
                        ScheduleTime(
                            stopId = "stop_1",
                            routeId = "route_1",
                            tripId = "trip_1",
                            arrivalTime = "12:00"
                        ),
                        ScheduleTime(
                            stopId = "stop_1",
                            routeId = "route_1",
                            tripId = "trip_1",
                            arrivalTime = "12:09"
                        ),
                        ScheduleTime(
                            stopId = "stop_1",
                            routeId = "route_1",
                            tripId = "trip_1",
                            arrivalTime = "12:11"
                        ),
                        ScheduleTime(
                            stopId = "stop_1",
                            routeId = "route_1",
                            tripId = "trip_1",
                            arrivalTime = "10:03"
                        ),
                        ScheduleTime(
                            stopId = "stop_1",
                            routeId = "route_1",
                            tripId = "trip_1",
                            arrivalTime = "18:31"
                        )
                    ),
                    routes = mapOf(
                        "1" to Route(
                            routeId = "route_1",
                            routeName = "213",
                            textColor = "FFFFFF",
                            backgroundColor = "009EE3",
                            routeType = VehicleIcon.BKK_BUS,
                            iconDisplayType = ShapeType.BOX
                        )
                    )
                ),
                onTripSelect = {}
            )
        }
    }
}