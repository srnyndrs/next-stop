package com.srnyndrs.next_stop.shared.domain.model.combined

import com.srnyndrs.next_stop.shared.domain.model.single.Departure
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.single.Trip

data class NearbyDepartures(
    val departures: Map<String, List<Departure>>,
    val stops: Map<String, Stop>,
    val trips: Map<String, Trip>,
    val routes: Map<String, Route>
)