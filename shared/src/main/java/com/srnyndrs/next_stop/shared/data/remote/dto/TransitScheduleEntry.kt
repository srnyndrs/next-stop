package com.srnyndrs.next_stop.shared.data.remote.dto

import android.health.connect.ReadRecordsRequestUsingIds
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitScheduleEntry(
    val limitExceeded: Boolean,
    val entry: TransitSchedule,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitSchedule(
    val stopId: String,
    @Deprecated("Use date instead")
    val serviceDate: String,
    val date: String,
    val routeIds: List<String>,
    val nearbyStopIds: List<String>,
    val alertIds: List<String>,
    val schedules: List<TransitRouteSchedule>
)

@Serializable
data class TransitRouteSchedule(
    val routeId: String,
    val alertIds: List<String>,
    val directions: List<TransitRouteScheduleForDirection>
)

@Serializable
data class TransitRouteScheduleForDirection(
    val directionId: String? = null,
    val groups: Map<String, TransitScheduleGroup>,
    val stopTimes: List<TransitScheduleStopTime>
)

@Serializable
data class TransitScheduleGroup(
    val groupId: String,
    val headsign: String,
    val description: String
)
