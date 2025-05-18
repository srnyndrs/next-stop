package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitTripPlanResultEntry(
    val limitExceeded: Boolean,
    val entry: TransitTripResponseEntry,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitTripResponseEntry(
    val requestParameters: Map<String, String>,
    val plan: TransitTripPlan,
    val error: TransitPlannerError? = null
)

@Serializable
data class TransitTripPlan(
    val date: String,
    val from: TransitPlace? = null,
    val to: TransitPlace? = null,
    val itineraries: List<TransitItinerary>
)

@Serializable
data class TransitPlace(
    val name: String
)

@Serializable
data class TransitItinerary(
    val duration: Long,
    val startTime: Long,
    val endTime: Long,
    val walkTime: Long,
    val bikeTime: Long,
    val transitTime: Long,
    val waitingTime: Long,
    val bikeDistance: Double,
    val walkDistance: Double,
    val walkLimitExceeded: Boolean,
    val displayedLegs: List<TransitDisplayedLeg>
)

@Serializable
data class TransitDisplayedLeg(
    val first: Boolean? = null,
    val time: Long,
    val walkTo: Boolean,
    val name: String,
    val type: String,
    val routeId: String? = null,
    val routeIds: List<String>? = null,
    val wheelchairAccessible: Boolean? = null,
    val hasAlert: Boolean? = null,
    val last: Boolean? = null
)

@Serializable
data class TransitPlannerError(
    val id: Int,
    val message: List<String>,
    val missing: List<String>,
    val noPath: Boolean
)