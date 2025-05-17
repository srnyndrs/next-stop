package com.srnyndrs.next_stop.app.presentation.sample

import com.srnyndrs.next_stop.shared.data.remote.dto.ShapeType
import com.srnyndrs.next_stop.shared.domain.model.single.Alert
import com.srnyndrs.next_stop.shared.domain.model.single.Departure
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.single.Trip
import com.srnyndrs.next_stop.shared.domain.model.single.VehicleIcon
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails

fun testStopA() = Stop(
    stopId = "stop_1",
    stopName = "Etele út / Fehérvári út",
    direction = 70f,
    location = 47.3 to 19.7,
    colors = listOf(
        "FFD800",
        "000000",
        "009EE3"
    )
)

fun testStopB() = Stop(
    stopId = "stop_2",
    stopName = "Hengermalom út",
    direction = 20f,
    location = 47.3 to 19.7,
    colors = listOf(
        "FFD800",
        "009EE3"
    )
)

fun testTripA() = Trip(
    tripId = "trip_1",
    tripHeadsign = "Bem rakpart ► Bécsi út / Vörösvári út",
    wheelchairAccessible = false
)

fun testTripB() = Trip(
    tripId = "trip_2",
    tripHeadsign = "Donát-hegy",
    wheelchairAccessible = true
)

fun testTripC() = Trip(
    tripId = "trip_3",
    tripHeadsign = "Savoya Park ► Bécsi út / Vörösvári út",
    wheelchairAccessible = false
)

fun testRouteA() = Route(
    routeId = "route_1",
    routeName = "41",
    textColor = "000000",
    backgroundColor = "FFD800",
    routeType = VehicleIcon.BKK_TRAM,
    iconDisplayType = ShapeType.BOX
)

fun testRouteB() = Route(
    routeId = "route_2",
    routeName = "213",
    textColor = "FFFFFF",
    backgroundColor = "009EE3",
    routeType = VehicleIcon.BKK_BUS,
    iconDisplayType = ShapeType.BOX
)

fun testRouteC() = Route(
    routeId = "route_3",
    routeName = "2",
    textColor = "FFFFFF",
    backgroundColor = "E41F18",
    routeType = VehicleIcon.BKK_SUBWAY,
    iconDisplayType = ShapeType.CIRCLE
)

fun testDepartureA() = Departure(
    stopId = "stop_1",
    routeId = "route_1",
    tripId = "trip_1",
    arrivalTime = "2'",
    wheelchairAccessible = true,
    alertIds = listOf(
        testAlertA().alertId
    )
)

fun testDepartureB() = Departure(
    stopId = "stop_2",
    routeId = "route_2",
    tripId = "trip_2",
    arrivalTime = "5'",
    wheelchairAccessible = true,
    alertIds = emptyList()
)

fun testDepartureC() = Departure(
    stopId = "stop_1",
    routeId = "route_3",
    tripId = "trip_3",
    arrivalTime = "4\'",
    wheelchairAccessible = true,
    alertIds = emptyList()
)

fun testAlertA() = Alert(
    alertId = "alert_1",
    description = "Az Infopark megállónál akadálymentesen nem érhető el"
)

fun testAlertB() = Alert(
    alertId = "alert_2",
    description = "Alert 2"
)

fun testStopC() = Stop(
    stopId = "stop_3",
    stopName = "Kelenföld vasútállomás",
    direction = 70f,
    location = 47.3 to 19.7
)

fun testNearbyDepartures() = NearbyDepartures(
    departures = listOf(testDepartureA(),testDepartureB(), testDepartureC()).groupBy { it.stopId },
    stops = listOf(testStopA(), testStopB()).associateBy { it.stopId },
    trips = listOf(testTripA(), testTripB(), testTripC()).associateBy { it.tripId },
    routes = listOf(testRouteA(), testRouteB(), testRouteC()).associateBy { it.routeId }
)

fun testTripDetailsA() = TripDetails(
    trip = testTripA(),
    route = testRouteA(),
    schedule = mapOf(
        testStopA() to "11:22",
        testStopB() to "11:25",
        testStopC() to "11:30"
    ),
    currentStopSequence = 2,
    alerts = listOf(testAlertA())
)

