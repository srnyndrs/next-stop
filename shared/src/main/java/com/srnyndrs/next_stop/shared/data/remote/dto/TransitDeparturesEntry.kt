package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitDeparturesEntry(
    val limitExceeded: Boolean,
    val references: TransitReferences,
    val entry: TransitArrivalsAndDepartures,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitArrivalsAndDepartures(
    val stopId: String,
    val routeIds: List<String>,
    val alertIds: List<String>,
    val nearbyStopIds: List<String>,
    val stopTimes: List<TransitScheduleStopTime>
)