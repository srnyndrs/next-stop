package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransitReferences(
    val agencies: Map<String, TransitAgency>,
    val routes: Map<String, TransitRoute>,
    val stops: Map<String, TransitStop>,
    val trips: Map<String, TransitTrip>,
    val alerts: Map<String, TransitAlert>
)

@Serializable
data class TransitAgency(
    val id: String,
    val name: String,
    val url: String,
    val timezone: String,
    val lang: String? = null,
    val phone: String? = null,
)

@Serializable
data class TransitRoute(
    val id: String,
    val shortName: String,
    val longName: String? = null,
    val description: String? = null,
    val type: RouteType,
    val url: String? = null,
    val agencyId: String? = null,
    val bikesAllowed: Boolean,
    val style: TransitRouteStyle,
    val sortOrder: Int
)

@Serializable
data class TransitRouteStyle(
    val color: String? = null,
    val stop: TransitStopStyle? = null,
    val icon: TransitRouteStyleIcon? = null,
    val vehicleIcon: TransitVehicleStyleIcon? = null
)

@Serializable
data class TransitStopStyle(
    val colors: List<String?>? = null,
    val type: String? = null,
    val image: String? = null
)

@Serializable
data class TransitRouteStyleIcon(
    val type: String, // BOX, CIRCLE enum
    val text: String,
    val textColor: String
)

@Serializable
data class TransitVehicleStyleIcon(
    val name: String? = null
)

@Serializable
data class TransitStop(
    val id: String,
    val vertex: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val code: String? = null,
    val direction: String,
    val platformCode: String? = null,
    val description: String? = null,
    val locationType: Int,
    val locationSubType: String? = null,
    val parentStationId: String? = null,
    val wheelchairBoarding: Boolean,
    val routeIds: List<String>,
    val alertIds: List<String?>? = null,
    val style: TransitStopStyle
)

@Serializable
data class TransitTrip(
    val id: String,
    val routeId: String,
    val shapeId: String,
    val blockId: String? = null,
    val tripHeadsign: String,
    val tripShortName: String? = null,
    val serviceId: String? = null,
    val directionId: String? = null,
    val bikesAllowed: Boolean,
    val wheelchairAccessible: Boolean
)

@Serializable
data class TransitAlert(
    val id: String,
    val start: Long,
    val end: Long? = null,
    val timestamp: Long,
    val modifiedTime: Long,
    val stopIds: List<String>,
    val routeIds: List<String>,
    val url: TranslatedString? = null,
    val header: TranslatedString? = null,
    val description: TranslatedString? = null,
    val disableApp: Boolean? = true,
    val startText: TranslatedString? = null,
    val endText: TranslatedString? = null,
    val routes: List<TransitAlertRoute>
)

@Serializable
data class TranslatedString(
    val translations: Map<String,String>,
    val someTranslation: String
)

@Serializable
data class TransitAlertRoute(
    val routeId: String,
    val stopIds: List<String>,
    val header: TranslatedString? = null,
    val effectType: String
)