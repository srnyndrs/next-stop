package com.srnyndrs.next_stop.shared.data.remote

import com.srnyndrs.next_stop.shared.BuildConfig
import com.srnyndrs.next_stop.shared.data.remote.dto.ApiResponse
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitDepartureGroupEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitDeparturesEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitRouteDetailsEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitScheduleEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitSearchEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitStopListEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTripDetailsEntry
import com.srnyndrs.next_stop.shared.data.remote.dto.TransitTripPlanResultEntry
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class TransportServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): TransportService {

    override suspend fun fetchDeparturesForLocation(
        lat: Double,
        lon: Double,
        radius: Int,
        minutesAfter: Int
    ): Response<TransitDepartureGroupEntry> {
        val response = httpClient.get {
            url(Constants.DEPARTURES_FOR_LOCATION)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("onlyDepartures", true)
            parameter("minutesBefore", 0)
            parameter("minutesAfter", minutesAfter)
            parameter("radius", radius)
            parameter("limit", 15)
            parameter("includeRouteId", null)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitDepartureGroupEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchTripDetails(
        tripId: String
    ): Result<ApiResponse<TransitTripDetailsEntry>> {
        val response = httpClient.get {
            url(Constants.TRIP_DETAILS)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("tripId", tripId)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitTripDetailsEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchStopsForLocation(
        lat: Double,
        lon: Double,
        latSpan: Double,
        lonSpan: Double
    ): Result<ApiResponse<TransitStopListEntry>> {
        val response = httpClient.get {
            url(Constants.STOPS_FOR_LOCATION)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("latSpan", latSpan)
            parameter("lonSpan", lonSpan)
        }

        return try {
            val responseModel: ApiResponse<TransitStopListEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchDeparturesForStop(stopId: String): Result<ApiResponse<TransitDeparturesEntry>> {
        val response = httpClient.get {
            url(Constants.DEPARTURES_FOR_STOP)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("stopId", stopId)
            parameter("onlyDepartures", true)
            parameter("minutesBefore", 0)
            parameter("minutesAfter", 60)
            parameter("limit", 25)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitDeparturesEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchScheduleForStop(
        stopId: String,
        date: String
    ): Result<ApiResponse<TransitScheduleEntry>> {
        val response = httpClient.get {
            url(Constants.SCHEDULE_FOR_STOP)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("stopId", stopId)
            parameter("date", date)
            parameter("onlyDepartures", true)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitScheduleEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchRouteDetails(
        routeId: String,
        date: String
    ): Result<ApiResponse<TransitRouteDetailsEntry>> {
        val response = httpClient.get {
            url(Constants.ROUTE_DETAILS)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("routeId", routeId)
            parameter("date", date)
            parameter("onlyDepartures", true)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitRouteDetailsEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchByQuery(query: String): Result<ApiResponse<TransitSearchEntry>> {
        val response = httpClient.get {
            url(Constants.SEARCH)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("query", query)
            parameter("includeReferences", true)
        }

        return try {
            val responseModel: ApiResponse<TransitSearchEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchTripPlans(
        source: String,
        destination: String,
        date: String,
        time: String,
        isArriveBy: Boolean,
        mode: List<String>
    ): Response<TransitTripPlanResultEntry> {
        val response = httpClient.get {
            url(Constants.PLAN_TRIP)
            parameter("key", BuildConfig.BKK_API_KEY)
            parameter("fromPlace", source)
            parameter("toPlace", destination)
            parameter("arriveBy", isArriveBy)
            parameter("date", date)
            parameter("time", time)
            parameter("mode", mode.joinToString(","))
            parameter("includeReferences", true)
            parameter("showIntermediateStops", true)
        }

        return try {
            val responseModel: ApiResponse<TransitTripPlanResultEntry> = response.body()
            Result.success(responseModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}