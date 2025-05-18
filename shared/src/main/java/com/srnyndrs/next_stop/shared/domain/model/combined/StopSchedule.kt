package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.ScheduleTime

data class StopSchedule(
    val stopId: String,
    val schedule: List<ScheduleTime>,
    val routes: Map<String, Route>
)
