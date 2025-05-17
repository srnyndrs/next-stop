package com.srnyndrs.next_stop.app.presentation.screen_trip_planner

// TODO
data class TripPlanState(
    val from: String? = null,
    val to: String? = null,
    val suggestions: List<String> = emptyList()
)
