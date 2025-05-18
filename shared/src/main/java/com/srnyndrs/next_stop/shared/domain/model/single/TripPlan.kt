package com.srnyndrs.next_stop.shared.domain.model.single

data class TripPlan(
    val duration: Long,
    val startTime: String,
    val endTime: String,
    val points: List<TripPoint>
)

data class TripPoint (
    val type: String,
    val name: String,
    val time: String,
    val routeId: String? = null
)
