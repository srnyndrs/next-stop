package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitTripDetailsEntry(
    val limitExceeded: Boolean,
    val entry: TransitTripDetails,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitTripDetails(
    val tripId: String,
    val serviceDate: String,
    val vertex: String? = null,
    val vehicle: TransitVehicle? = null,
    val polyline: TransitPolyline,
    val alertIds: List<String>,
    val stopTimes: List<TransitTripStopTime>,
    val nextBlockTripId: String? = null,
    val mayRequireBooking: Boolean
)

@Serializable
data class TransitVehicle(
    val vehicleId: String,
    val stopId: String,
    val stopSequence: Int? = null,
    val routeId: String,
    val bearing: Float,
    val location: TransitCoordinatePoint,
    val serviceDate: String,
    val licensePlate: String,
    val label: String? = null,
    val model: String? = null,
    val deviated: Boolean,
    val stale: Boolean? = null,
    val lastUpdateTime: Long,
    val status: String, // INCOMING_AT, STOPPED_AT, IN_TRANSIT_TO
    val congestionLevel: String? = null, // UNKNOWN, CONGESTION
    val stopDistancePercent: Int,
    val wheelchairAccessible: Boolean,
    val occupancy: TransitVehicleOccupancy? = null,
    val capacity: TransitVehicleOccupancy? = null,
    val tripId: String? = null,
    val vertex: String,
    val style: TransitVehicleStyle? = null
)

@Serializable
data class TransitPolyline(
    val levels: String,
    val points: String,
    val length: Int
)

@Serializable
data class TransitTripStopTime(
    val stopId: String,
    val stopHeadsign: String,
    val arrivalTime: Long? = null,
    val departureTime: Long? = null,
    val predictedArrivalTime: Long? = null,
    val predictedDepartureTime: Long? = null,
    val uncertain: Boolean? = null,
    val requiresBooking: Boolean,
    val stopSequence: Int,
    val shapeDistTraveled: Double? = null
)

@Serializable
data class TransitCoordinatePoint(
    val lat: Float,
    val lon: Float
)

@Serializable
data class TransitVehicleOccupancy(
    val adults: Int? = null,
    val children: Int? = null,
    val strollers: Int? = null,
    val wheelchairs: Int? = null,
    val other: Int? = null,
)

@Serializable
data class TransitVehicleStyle(
    val icon: TransitVehicleStyleIcon? = null
)