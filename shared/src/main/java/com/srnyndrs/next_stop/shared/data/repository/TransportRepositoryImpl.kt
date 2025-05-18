package com.srnyndrs.next_stop.shared.data.repository

import com.srnyndrs.next_stop.shared.data.mapper.getDepartureTime
import com.srnyndrs.next_stop.shared.data.mapper.toDomain
import com.srnyndrs.next_stop.shared.data.mapper.toRoute
import com.srnyndrs.next_stop.shared.data.mapper.toSearchResult
import com.srnyndrs.next_stop.shared.data.remote.TransportService
import com.srnyndrs.next_stop.shared.data.remote.dto.ResponseStatus
import com.srnyndrs.next_stop.shared.data.remote.dto.RouteType
import com.srnyndrs.next_stop.shared.data.remote.exceptions.ResponseException
import com.srnyndrs.next_stop.shared.data.remote.exceptions.RouteNotFoundException
import com.srnyndrs.next_stop.shared.data.remote.exceptions.TripNotFoundException
import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures
import com.srnyndrs.next_stop.shared.domain.model.combined.RouteDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.TripPlanResult
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import javax.inject.Inject

class TransportRepositoryImpl @Inject constructor(
    private val transportService: TransportService
): TransportRepository {

    override suspend fun fetchDeparturesForLocation(
        lat: Double,
        lon: Double,
        radius: Int,
        minutesAfter: Int
    ): Result<NearbyDepartures> {
        return transportService.fetchDeparturesForLocation(
            lat = lat,
            lon = lon,
            radius = radius,
            minutesAfter = minutesAfter
        ).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val departureGroupList = response.data.list
                val references = response.data.references

                val departures = departureGroupList.flatMap { departureGroup ->
                    val routeId = departureGroup.routeId
                    departureGroup.stopTimes.map { stopTime ->
                        stopTime.toDomain(routeId)
                    }
                }.groupBy { departure -> departure.stopId }

                val stops = references.stops.mapValues { (_, stop) -> stop.toDomain() }
                val trips = references.trips.mapValues { (_, trip) -> trip.toDomain() }
                val routes = references.routes.mapValues { (_, route) -> route.toDomain() }

                val nearbyDepartures = NearbyDepartures(
                    departures = departures,
                    stops = stops,
                    trips = trips,
                    routes = routes
                )

                Result.success(nearbyDepartures)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun fetchTripDetails(tripId: String): Result<TripDetails> {
        return transportService.fetchTripDetails(
            tripId = tripId
        ).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val tripDetails = response.data.entry
                val references = response.data.references

                // Try to get trip and route references
                val trip = references.trips[tripDetails.tripId] ?: return Result.failure(TripNotFoundException())
                val route = references.routes[trip.routeId] ?: return Result.failure(RouteNotFoundException())

                val stopTimes = buildMap {
                    tripDetails.stopTimes.map { stopTime ->
                        // Get stop by id
                        references.stops[stopTime.stopId]?.let { stop ->
                            put(stop.toDomain(), stopTime.getDepartureTime())
                        }
                    }
                }

                val alerts = tripDetails.alertIds.mapNotNull { alertId ->
                    references.alerts[alertId]?.toDomain()
                }

                val currentStopSequence = tripDetails.vehicle?.stopSequence

                val result = TripDetails(
                    trip = trip.toDomain(),
                    route = route.toDomain(),
                    schedule = stopTimes,
                    alerts = alerts,
                    currentStopSequence = currentStopSequence
                )

                Result.success(result)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun fetchStopsForLocation(
        location: Location,
        locationSpan: Location
    ): Result<List<Stop>> {
        return transportService.fetchStopsForLocation(
            lat = location.latitude(),
            lon = location.longitude(),
            latSpan = locationSpan.latitude(),
            lonSpan = locationSpan.longitude()
        ).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data

                val stops = data.list
                    .filter{ transitStop ->
                        transitStop.routeIds.isNotEmpty()
                    }
                    .map { transitStop ->
                        transitStop.toDomain()
                    }

                Result.success(stops)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun fetchDeparturesForStop(stopId: String): Result<StopDetails> {
        return transportService.fetchDeparturesForStop(
            stopId = stopId
        ).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data.entry
                val references = response.data.references

                val departures = data.stopTimes.mapNotNull { stopTime ->
                    references.trips[stopTime.tripId]?.let { trip ->
                        stopTime.toDomain(trip.routeId)
                    }
                }

                val stops = references.stops.mapValues { (_, stop) -> stop.toDomain() }
                val trips = references.trips.mapValues { (_, trip) -> trip.toDomain() }
                val routes = references.routes.mapValues { (_, route) -> route.toDomain() }

                val nearbyDepartures = StopDetails(
                    departures = departures,
                    stops = stops,
                    trips = trips,
                    routes = routes
                )

                Result.success(nearbyDepartures)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun fetchScheduleForStop(stopId: String, date: String): Result<StopSchedule> {
        return transportService.fetchScheduleForStop(stopId, date).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data.entry
                val references = response.data.references

                val routes = references.routes
                    .filter { data.routeIds.contains(it.key) }
                    .mapValues { (_, route) -> route.toDomain() }

                val schedule = data.schedules.flatMap { (routeId, _ , directions) ->
                    directions.flatMap { (_, _, stopTimes) ->
                        stopTimes.map { stopTime ->
                            stopTime.toDomain(stopId, routeId)
                        }
                    }
                }

                val result = StopSchedule(
                    stopId = stopId,
                    schedule = schedule,
                    routes = routes
                )

                Result.success(result)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun fetchRouteDetails(routeId: String, date: String): Result<RouteDetails> {
        return transportService.fetchRouteDetails(routeId, date).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data
                val references = data.references

                val route = data.entry.toRoute()

                val description = data.entry.description

                val variants = data.entry.variants.map { it.toDomain() }

                val stops = references.stops.mapValues { (_, stop) -> stop.toDomain() }

                val result = RouteDetails(
                    route = route,
                    description = description,
                    variants = variants,
                    stops = stops
                )

                Result.success(result)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun searchByQuery(query: String): Result<List<SearchResult>> {
        return transportService.searchByQuery(query).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data
                val references = data.references

                val routes = data.entry.routeIds?.mapNotNull { routeId ->
                    references.routes[routeId]?.toDomain()?.toSearchResult()
                } ?: emptyList()

                val stops = data.entry.stopIds?.mapNotNull { stopId ->
                    references.stops[stopId]?.toDomain()?.toSearchResult()
                } ?: emptyList()

                val result = routes + stops

                Result.success(result)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun fetchTripPlan(
        source: String,
        destination: String,
        date: String,
        time: String,
        isArriveBy: Boolean,
        mode: List<RouteType>
    ): Result<TripPlanResult> {
        return transportService.fetchTripPlans(source,destination,date,time,isArriveBy, mode.map { it.name }).fold(
            onSuccess = { response ->
                // Check the response status
                if(response.status != ResponseStatus.OK) {
                    return Result.failure(ResponseException(response.status))
                }

                // Process data
                val data = response.data
                val references = data.references

                val tripPlanResult = TripPlanResult(
                    plans = data.entry.plan.itineraries.map { it.toDomain() },
                    routes = references.routes.map { it.key to it.value.toDomain() }.toMap()
                )

                Result.success(tripPlanResult)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}