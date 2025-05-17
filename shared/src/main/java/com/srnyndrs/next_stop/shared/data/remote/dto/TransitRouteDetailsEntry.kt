package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransitRouteDetailsEntry(
    val limitExceeded: Boolean,
    val entry: TransitRouteDetails,
    val references: TransitReferences,
    @SerialName("class")
    val clazz: String
)

@Serializable
data class TransitRouteDetails(
    val id: String,
    val shortName: String,
    val longName: String? = null,
    val description: String? = null,
    val type: RouteType,
    val url: String? = null,
    val agencyId: String? = null,
    val bikesAllowed: Boolean = false,
    val style: TransitRouteStyle,
    val sortOrder: Int,
    val variants: List<TransitRouteVariant>,
    val alertIds: List<String>
)

@Serializable
data class TransitRouteVariant(
    val name: String,
    val stopIds: List<String>,
    val mayRequireBooking: Boolean? = null,
    val bookableStops: List<String>? = null,
    val direction: String? = null,
    val headsign: String,
    val polyline: TransitPolyline? = null,
    val routeId: String? = null,
    val type: String? = null
)