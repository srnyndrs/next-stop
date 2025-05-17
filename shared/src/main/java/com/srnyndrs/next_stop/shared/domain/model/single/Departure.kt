package com.srnyndrs.next_stop.shared.domain.model.single

data class Departure(
    val routeId: String,
    val stopId: String,
    val tripId: String,
    val arrivalTime: String,
    val wheelchairAccessible: Boolean,
    val alertIds: List<String>
)
