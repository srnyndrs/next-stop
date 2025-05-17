package com.srnyndrs.next_stop.shared.domain.repository

import com.srnyndrs.next_stop.shared.data.remote.dto.RouteType
import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures
import com.srnyndrs.next_stop.shared.domain.model.combined.RouteDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.TripPlanResult

interface TransportRepository {
    suspend fun fetchDeparturesForLocation(lat: Double, lon: Double, radius: Int, minutesAfter: Int): Result<NearbyDepartures>
    suspend fun fetchTripDetails(tripId: String): Result<TripDetails>
    suspend fun fetchStopsForLocation(location: Location, locationSpan: Location): Result<List<Stop>>
    suspend fun fetchDeparturesForStop(stopId: String): Result<StopDetails>
    suspend fun fetchScheduleForStop(stopId: String, date: String): Result<StopSchedule>
    suspend fun fetchRouteDetails(routeId: String, date: String): Result<RouteDetails>
    suspend fun searchByQuery(query: String): Result<List<SearchResult>>
    suspend fun fetchTripPlan(source: String, destination: String, date: String, time: String, isArriveBy: Boolean, mode: List<RouteType>): Result<TripPlanResult>
}