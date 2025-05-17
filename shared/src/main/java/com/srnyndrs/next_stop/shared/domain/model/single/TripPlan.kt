package com.srnyndrs.next_stop.shared.domain.model.single

data class TripPlan(
    val duration: Long,
    val startTime: String,
    val endTime: String,
    val points: List<TripPoint>
)

interface TripPoint {
    val type: String // walk, route
    val name: String // Origin, Destination
    val time: String
}

data class RouteTripPoint(
    override val type: String,
    override val name: String,
    override val time: String,
    val routeId: String? = null,
    val wheelchairAccessible: Boolean? = null,
    val hasAlert: Boolean? = null
): TripPoint

data class WalkTripPoint(
    override val type: String,
    override val name: String,
    override val time: String
): TripPoint
