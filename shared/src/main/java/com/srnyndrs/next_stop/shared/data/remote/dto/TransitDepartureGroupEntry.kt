package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitDepartureGroupEntry(
    val list: List<TransitDepartureGroup>,
    val outOfRange: Boolean,
    val limitExceeded: Boolean,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitDepartureGroup(
    val routeId: String,
    val headsign: String,
    val stopTimes: List<TransitScheduleStopTime>
)

@Serializable
data class TransitScheduleStopTime(
    val stopId: String? = null,
    val stopHeadsign: String,
    val arrivalTime: Long? = null,
    val departureTime: Long? = null,
    val predictedArrivalTime: Long? = null,
    val predictedDepartureTime: Long? = null,
    val uncertain: Boolean? = null,
    val tripId: String,
    val serviceDate: String,
    val wheelchairAccessible: Boolean,
    val mayRequireBooking: Boolean,
    val alertIds: List<String>
)