package com.srnyndrs.next_stop.shared.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class RouteType {
    WALK,
    BICYCLE,
    CAR,
    TRAM,
    SUBWAY,
    SUBURBAN_RAILWAY,
    RAIL,
    COACH,
    BUS,
    TROLLEYBUS,
    FERRY,
    CABLE_CAR,
    GONDOLA,
    FUNICULAR,
    TRANSIT,
    TRAINISH,
    BUSISH,
    LEG_SWITCH,
    CUSTOM_MOTOR_VEHICLE
}