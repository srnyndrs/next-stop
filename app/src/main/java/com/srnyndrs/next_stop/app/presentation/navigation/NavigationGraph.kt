package com.srnyndrs.next_stop.app.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.srnyndrs.next_stop.app.presentation.components.search.SearchViewModel
import com.srnyndrs.next_stop.app.presentation.screen_favourites.FavouritesScreen
import com.srnyndrs.next_stop.app.presentation.screen_home.HomeScreen
import com.srnyndrs.next_stop.app.presentation.screen_home.HomeViewModel
import com.srnyndrs.next_stop.app.presentation.screen_map.MapScreen
import com.srnyndrs.next_stop.app.presentation.screen_map.MapViewModel
import com.srnyndrs.next_stop.app.presentation.screen_route_details.RouteDetailsScreen
import com.srnyndrs.next_stop.app.presentation.screen_route_details.RouteDetailsViewModel
import com.srnyndrs.next_stop.app.presentation.screen_settings.SettingsScreen
import com.srnyndrs.next_stop.app.presentation.screen_trip_details.TripDetailsScreen
import com.srnyndrs.next_stop.app.presentation.screen_trip_details.TripDetailsViewModel
import com.srnyndrs.next_stop.app.presentation.screen_trip_planner.TripPlannerScreen
import com.srnyndrs.next_stop.app.presentation.screen_trip_planner.TripPlannerViewModel
import com.srnyndrs.next_stop.shared.domain.model.combined.SearchResult

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = homeViewModel,
                navController = navController
            ) { uiEvent ->
                homeViewModel.onEvent(uiEvent)
            }
        }
        composable(
            route = Screen.MapScreen.route
        ) {
            val mapViewModel = hiltViewModel<MapViewModel>()
            val searchViewModel = hiltViewModel<SearchViewModel, SearchViewModel.SearchViewModelFactory> { factory ->
                factory.create(SearchResult.StopResult::class)
            }
            MapScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = mapViewModel,
                searchViewModel = searchViewModel,
                navController = navController,
                onEvent = { uiEvent ->
                    mapViewModel.onEvent(uiEvent)
                }
            )
        }
        composable(
            route = Screen.FavouritesScreen.route
        ) {
            val searchViewModel = hiltViewModel<SearchViewModel, SearchViewModel.SearchViewModelFactory> { factory ->
                factory.create(SearchResult.RouteResult::class)
            }
            FavouritesScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = hiltViewModel(),
                searchViewModel = searchViewModel,
                onItemSelect = { routeId ->
                    navController.navigate(Screen.RouteDetailsScreen.passRouteId(routeId))
                }
            )
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = hiltViewModel()
            )
        }
        composable(
            route = Screen.TripDetailsScreen.route,
            arguments = listOf(
                navArgument("tripId") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val tripIdArgument = navBackStackEntry.arguments?.getString("tripId")
            tripIdArgument?.let { tripId ->
                val viewModel = hiltViewModel<TripDetailsViewModel, TripDetailsViewModel.TripDetailsViewModelFactory> { factory ->
                    factory.create(tripId)
                }
                TripDetailsScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable(
            route = Screen.RouteDetailsScreen.route,
            arguments = listOf(
                navArgument("routeId") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val routeIdArgument = navBackStackEntry.arguments?.getString("routeId")
            routeIdArgument?.let { routeId ->
                val viewModel = hiltViewModel<RouteDetailsViewModel, RouteDetailsViewModel.RouteDetailsViewModelFactory> { factory ->
                    factory.create(routeId)
                }
                RouteDetailsScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable(
            route = Screen.TripPlannerScreen.route,
            arguments = listOf(
                navArgument("destination") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val destinationArgument = navBackStackEntry.arguments?.getString("destination")
            destinationArgument?.let { destination ->
                val tripPlannerViewModel = hiltViewModel<TripPlannerViewModel, TripPlannerViewModel.TripPlannerViewModelFactory> { factory ->
                    factory.create(destination)
                }
                TripPlannerScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = tripPlannerViewModel
                )
            }
        }
    }
}