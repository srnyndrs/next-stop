package com.srnyndrs.next_stop.shared.data.remote

import com.srnyndrs.next_stop.shared.data.remote.dto.ApiResponse
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitDepartureGroupEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitDeparturesEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitRouteDetailsEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitScheduleEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitSearchEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitStopListEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTripDetailsEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTripPlanResultEntry

typealias Response<T> = Result<ApiResponse<T>>

interface TransportService {
    suspend fun fetchDeparturesForLocation(lat: Double, lon: Double, radius: Int, minutesAfter: Int): Response<TransitDepartureGroupEntry>
    suspend fun fetchTripDetails(tripId: String): Response<TransitTripDetailsEntry>
    suspend fun fetchStopsForLocation(lat: Double, lon: Double, latSpan: Double, lonSpan: Double): Response<TransitStopListEntry>
    suspend fun fetchDeparturesForStop(stopId: String): Response<TransitDeparturesEntry>
    suspend fun fetchScheduleForStop(stopId: String, date: String): Response<TransitScheduleEntry>
    suspend fun fetchRouteDetails(routeId: String, date: String): Response<TransitRouteDetailsEntry>
    suspend fun searchByQuery(query: String): Response<TransitSearchEntry>
    suspend fun fetchTripPlans(source: String, destination: String, date: String, time: String, isArriveBy: Boolean, mode: List<String>): Response<TransitTripPlanResultEntry>
}