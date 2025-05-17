package com.srnyndrs.next_stop.shared.domain.model.single

data class ScheduleTime(
    val stopId: String,
    val routeId: String,
    val tripId: String,
    val arrivalTime: String
)