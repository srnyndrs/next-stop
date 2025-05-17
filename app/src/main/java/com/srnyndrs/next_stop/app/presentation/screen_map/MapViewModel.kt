package com.srnyndrs.next_stop.app.presentation.screen_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.srnyndrs.next_stop.app.domain.usecase.GetCurrentDateUseCase
import com.srnyndrs.next_stop.app.domain.usecase.GetStopDetailsUseCase
import com.srnyndrs.next_stop.app.domain.usecase.GetStopScheduleUseCase
import com.srnyndrs.next_stop.app.domain.usecase.GetStopsByLocationUseCase
import com.srnyndrs.next_stop.app.domain.usecase.location.GetUserLocationUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.single.Location
import com.srnyndrs.next_stop.shared.domain.model.single.latitude
import com.srnyndrs.next_stop.shared.domain.model.single.longitude
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getStopsByLocationUseCase: GetStopsByLocationUseCase,
    private val getStopDetailsUseCase: GetStopDetailsUseCase,
    private val getStopScheduleUseCase: GetStopScheduleUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val getCurrentDateUseCase: GetCurrentDateUseCase
): ViewModel() {

    private val _state = MutableStateFlow(GoogleMapState())
    val state = _state.asStateFlow()
        .onStart { getUserLocation() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            GoogleMapState()
        )

    private fun getUserLocation() = viewModelScope.launch(Dispatchers.IO) {
        getUserLocationUseCase().fold(
            onSuccess = { location ->
                val cameraPosition = _state.value.cameraPosition
                _state.value = _state.value.copy(
                    cameraPosition = CameraPosition
                        .fromLatLngZoom(
                            LatLng(location.latitude(), location.longitude()),
                            cameraPosition.zoom
                        )
                )
            },
            onFailure = {}
        )
    }

    private fun onLocationChange(
        location: Location,
        locationSpan: Location
    ) = viewModelScope.launch(Dispatchers.IO) {
        getStopsByLocationUseCase(location = location, locationSpan = locationSpan).fold(
            onSuccess = { _state.value = _state.value.copy(stops = it, isLoading = false) },
            onFailure = { _state.value = _state.value.copy(stops = emptyList(), isLoading = false) }
        )
    }

    /*private fun onSelectionChange(stop: Stop) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(selectedStop = stop, isLoading = true)
        getStopDetailsUseCase(stopId = stop.stopId)
            .onSuccess { _state.value = _state.value.copy(stopDetails = it, isLoading = false, error = null) }
            .onFailure { _state.value = _state.value.copy(stopDetails = null, isLoading = false, error = it) }
    }*/

    private fun getStopDetails(stopId: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(stopDetails = UiState.Loading())
        getStopDetailsUseCase(stopId = stopId)
            .onSuccess { stopDetails ->
                _state.value = _state.value.copy(stopDetails = UiState.Success(stopDetails))
            }
            .onFailure { error ->
                _state.value = _state.value.copy(
                    stopDetails = UiState.Error(message = error.message ?: "An error occurred")
                )
            }
    }

    private fun getStopSchedule(stopId: String, date: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = _state.value.copy(stopSchedule = UiState.Loading())
        getStopScheduleUseCase(stopId = stopId, date = date)
            .onSuccess { _state.value = _state.value.copy(stopSchedule = UiState.Success(data = it)) }
            .onFailure { _state.value = _state.value.copy(stopSchedule = UiState.Error(message = it.message ?: "An error occurred")) }
    }

    fun onEvent(event: MapScreenEvent) {
        when(event) {
            is MapScreenEvent.MapBoundsChangeEvent -> {
                val bounds = event.bounds
                val lat = bounds.center.latitude
                val lon = bounds.center.longitude
                val latSpan = abs(bounds.center.latitude - bounds.northeast.latitude)
                val lonSpan = abs(bounds.center.longitude - bounds.northeast.longitude)
                onLocationChange(
                    location = lat to lon,
                    locationSpan = latSpan to lonSpan
                )
            }
            is MapScreenEvent.MarkerClickEvent -> {
                _state.value = _state.value.copy(selectedStop = event.stop)
            }
            MapScreenEvent.MapClickEvent -> {
                // Clear selection
                _state.value = _state.value.copy(selectedStop = null, stopDetails = UiState.Empty())
            }
            is MapScreenEvent.GetStopDetailsEvent -> {
                getStopDetails(event.stopId)
                getCurrentDateUseCase()?.let { date ->
                    getStopSchedule(event.stopId, date) // TODO
                } ?: _state.value.copy(stopSchedule = UiState.Empty())
            }
        }
    }
}