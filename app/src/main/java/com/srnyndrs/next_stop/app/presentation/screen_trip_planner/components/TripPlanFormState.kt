package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import com.srnyndrs.next_stop.app.presentation.util.formatDateWithPattern

data class TripPlanFormState(
    val date: Long,
    val hour: Int,
    val minutes: Int,
    val isArriveBy: Boolean = false
)

fun TripPlanFormState.isValid(): Boolean {
    return date.formatDateWithPattern("yyyy-MM-dd") != null
            && hour in 1..23
            && minutes in 0..59
}
