package com.srnyndrs.next_stop.app.presentation.screen_trip_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.GetTripDetailsUseCase
import com.srnyndrs.next_stop.app.domain.usecase.route.AddRouteUseCase
import com.srnyndrs.next_stop.app.domain.usecase.route.DeleteRouteUseCase
import com.srnyndrs.next_stop.app.domain.usecase.route.GetFavouriteRouteByIdUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.combined.TripDetails
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TripDetailsViewModel.TripDetailsViewModelFactory::class)
class TripDetailsViewModel @AssistedInject constructor(
    @Assisted val tripId: String,
    private val getTripDetailsUseCase: GetTripDetailsUseCase,
    private val getFavouriteRouteByIdUseCase: GetFavouriteRouteByIdUseCase,
    private val addRouteUseCase: AddRouteUseCase,
    private val deleteRouteUseCase: DeleteRouteUseCase
): ViewModel() {

    @AssistedFactory
    interface TripDetailsViewModelFactory {
        fun create(tripId: String): TripDetailsViewModel
    }

    private val _tripDetails = MutableStateFlow<UiState<TripDetails>>(UiState.Loading())
    val tripDetails = _tripDetails.asStateFlow()
        .onStart {
            fetchTripDetail(tripId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading()
        )

    private fun fetchTripDetail(tripId: String) = viewModelScope.launch(Dispatchers.IO) {
        getTripDetailsUseCase(tripId).fold(
                onSuccess = { tripDetails ->
                    val favourite = getFavouriteRouteByIdUseCase(tripDetails.route.routeId) != null
                    _tripDetails.value = UiState.Success(data = tripDetails.copy(favourite = favourite))
                },
                onFailure = {
                    _tripDetails.value = UiState.Error(message = it.message ?: "An error has occurred")
                }
        )
    }

    fun onEvent(event: TripDetailsScreenEvent) {
        when(event) {
            is TripDetailsScreenEvent.GetTripDetailsEvent -> {
                fetchTripDetail(event.tripId)
            }
            is TripDetailsScreenEvent.MarkRouteAsFavourite -> {
                val route = event.route
                addRouteUseCase(route = route)
            }
            is TripDetailsScreenEvent.RemoveRouteFromFavourites -> {
                val routeId = event.routeId
                deleteRouteUseCase(routeId = routeId)
            }
        }
    }
}