package com.srnyndrs.next_stop.app.presentation.screen_trip_details

import com.srnyndrs.next_stop.shared.domain.model.single.Route

sealed interface TripDetailsScreenEvent {
    data class GetTripDetailsEvent(val tripId: String): TripDetailsScreenEvent
    data class MarkRouteAsFavourite(val route: Route): TripDetailsScreenEvent
    data class RemoveRouteFromFavourites(val routeId: String): TripDetailsScreenEvent
}