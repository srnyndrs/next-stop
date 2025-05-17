package com.srnyndrs.next_stop.shared.domain.model.single

data class Trip(
    val tripId: String,
    val tripHeadsign: String,
    val wheelchairAccessible: Boolean
)
