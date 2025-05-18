package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.TripPlan

data class TripPlanResult(
    val plans: List<TripPlan>? = null,
    val routes: Map<String, Route>
)
