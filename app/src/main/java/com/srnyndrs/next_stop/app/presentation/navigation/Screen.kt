package com.srnyndrs.next_stop.app.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Map
import com.composables.icons.lucide.Settings

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object HomeScreen: Screen("screen_home", "Home", Lucide.House)
    data object MapScreen: Screen("screen_map", "Map", Lucide.Map)
    data object FavouritesScreen: Screen("screen_favourites", "Favourites", Lucide.Heart)
    data object SettingsScreen: Screen("screen_settings", "Settings", Lucide.Settings)
    data object TripDetailsScreen: Screen(
        route = "screen_trip_details/{${Args.TRIP_ID}}",
        title = "Trip Details"
    ) {
        object Args {
            const val TRIP_ID = "tripId"
        }
        fun passTripId(tripId: String) = "screen_trip_details/$tripId"
    }
    data object RouteDetailsScreen: Screen(
        route = "screen_route_details/{${Args.ROUTE_ID}}",
        title = "Route Details"
    ) {
        object Args {
            const val ROUTE_ID = "routeId"
        }
        fun passRouteId(routeId: String) = "screen_route_details/$routeId"
    }
    data object TripPlannerScreen: Screen(
        route = "screen_trip_planner/{${Args.DESTINATION}}",
        title = "Trip Planner"
    ) {
        object Args {
            const val DESTINATION = "destination"
        }
        fun passDestination(destination: String) = "screen_trip_planner/$destination"
    }
}