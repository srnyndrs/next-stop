package com.srnyndrs.next_stop.shared.data.mapper

import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitAlert
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitDisplayedLeg
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitItinerary
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitRoute
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitRouteDetails
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitRouteVariant
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitScheduleStopTime
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitStop
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTrip
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTripStopTime
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitVehicleStyleIcon
import com.srnyndrs.next_stop.shared.domain.model.single.Alert
import com.srnyndrs.next_stop.shared.domain.model.single.Departure
import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.RouteTripPoint
import com.srnyndrs.next_stop.shared.domain.model.single.RouteVariant
import com.srnyndrs.next_stop.shared.domain.model.single.ScheduleTime
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.single.Trip
import com.srnyndrs.next_stop.shared.domain.model.single.TripPlan
import com.srnyndrs.next_stop.shared.domain.model.single.TripPoint
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon
import com.srnyndrs.next_stop.shared.domain.model.single.WalkTripPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.max

fun TransitScheduleStopTime.toDomain(
    routeId: String
): Departure {
    return Departure(
        routeId = routeId,
        stopId = this.stopId ?: "unknown", // TODO
        tripId = this.tripId,
        alertIds = this.alertIds,
        wheelchairAccessible = this.wheelchairAccessible,
        arrivalTime = getTime(
            setOf(
                this.predictedDepartureTime,
                this.predictedArrivalTime,
                this.departureTime,
                this.arrivalTime
            )
        ).formatToDifference()
    )
}

fun TransitVehicleStyleIcon.toDomain(
    agencyId: String
): VehicleIcon {
    return try {
        this.name?.let {
            VehicleIcon.valueOf("${agencyId}_${it.replace('-','_')}".uppercase())
        } ?: VehicleIcon.UNKNOWN
    } catch (e: IllegalArgumentException) {
        VehicleIcon.UNKNOWN
    }
}

fun TransitStop.toDomain(): Stop {
    return Stop(
        stopId = this.id,
        stopName = this.name,
        location = this.let{lat to lon},
        direction = this.direction.toFloatOrNull(),
        colors = this.style.colors?.filterNotNull() ?: emptyList(),
        priority = this.routeIds.isNotEmpty()
    )
}

fun TransitTripStopTime.getDepartureTime() : String {
    return getTime(
        setOf(
            this.predictedDepartureTime,
            this.predictedArrivalTime,
            this.departureTime,
            this.arrivalTime
        )
    ).formatToDate()
}

fun TransitTrip.toDomain(): Trip {
    return Trip(
        tripId = this.id,
        tripHeadsign = this.tripHeadsign,
        wheelchairAccessible = wheelchairAccessible
    )
}

fun TransitRoute.toDomain(): Route {
    return Route(
        routeId = this.id,
        routeName = this.style.icon?.text ?: this.shortName,
        textColor = this.style.icon?.textColor ?: "FFFFFF",
        backgroundColor = this.style.color ?: "000000",
        iconDisplayType = ShapeType.valueOf(this.style.icon?.type ?: "BOX"),
        routeType = this.style.vehicleIcon?.toDomain(agencyId?.split("_")?.get(0) ?: "BKK") ?: VehicleIcon.UNKNOWN
    )
}

fun TransitAlert.toDomain(): Alert {
    return Alert(
        alertId = this.id,
        description = this.header?.translations?.get("hu") ?: "unknown" // TODO
    )
}

fun TransitRouteDetails.toRoute(): Route {
    return Route(
        routeId = this.id,
        routeName = this.shortName,
        textColor = this.style.icon?.textColor ?: "FFFFFF",
        backgroundColor = this.style.color ?: "000000",
        iconDisplayType = ShapeType.valueOf(this.style.icon?.type ?: "BOX"),
        routeType = this.style.vehicleIcon?.toDomain(agencyId?.split("_")?.get(0) ?: "BKK") ?: VehicleIcon.UNKNOWN
    )
}

fun TransitRouteVariant.toDomain(): RouteVariant {
    return RouteVariant(
        name = this.name,
        headsign = this.headsign,
        stopIds = stopIds,
        routePoints = this.polyline?.let { decodePolyline(it.points) }
    )
}

fun TransitScheduleStopTime.toDomain(
    stopId: String,
    routeId: String,
): ScheduleTime {
    return ScheduleTime(
        stopId = stopId,
        routeId = routeId,
        tripId = this.tripId,
        arrivalTime = getTime(setOf(
            this.predictedDepartureTime,
            this.predictedArrivalTime,
            this.departureTime,
            this.arrivalTime
        )).formatToDate()
    )
}

fun TransitItinerary.toDomain(): TripPlan {
    return TripPlan(
        duration = duration,
        startTime = startTime,
        endTime = endTime,
        points = this.displayedLegs.map { transitDisplayedLeg ->
            transitDisplayedLeg.toDomain()
        }
    )
}

fun TransitDisplayedLeg.toDomain(): TripPoint {
    val formattedTime = time.formatToDate()
    return when(this.type) {
        "route" -> RouteTripPoint(type, name, formattedTime, routeId, wheelchairAccessible, hasAlert)
        else -> WalkTripPoint(type, name, formattedTime)
    }
}

fun decodePolyline(encoded: String): List<Location> {
    val poly = ArrayList<Location>()
    var index = 0
    val len = encoded.length
    var lat = 0
    var lng = 0

    while (index < len) {
        var b: Int
        var shift = 0
        var result = 0

        do {
            b = encoded[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
        lat += dlat

        shift = 0
        result = 0
        do {
            b = encoded[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if ((result and 1) != 0) (result shr 1).inv() else result shr 1
        lng += dlng

        val p = Location(
            lat / 1E5,
            lng / 1E5
        )
        poly.add(p)
    }

    return poly
}

// Returns the first not null time or zero
fun getTime(times: Set<Long?>): Long {
    return times.find { it != null }?.times(1000) ?: 0L
}

fun Long.formatToDate(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return formatter.format(calendar.time)
}

fun Long.formatToDifference(): String {
    val currentTimeInMillis = System.currentTimeMillis()
    val difference = this - currentTimeInMillis
    val differenceInMinutes = max(0, difference / 60000)
    return if(differenceInMinutes > 0) "$differenceInMinutes\'" else "NOW" // TODO
}