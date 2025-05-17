package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Alert
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.single.Trip

data class TripDetails(
    val trip: Trip,
    val route: Route,
    val schedule: Map<Stop, String>,
    val alerts: List<Alert>,
    val currentStopSequence: Int? = null,
    val favourite: Boolean = false
)