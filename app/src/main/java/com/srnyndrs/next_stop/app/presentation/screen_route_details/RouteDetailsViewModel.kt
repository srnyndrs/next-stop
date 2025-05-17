package com.srnyndrs.next_stop.app.presentation.screen_route_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.route.GetRouteDetailsUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.combined.RouteDetails
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RouteDetailsViewModel.RouteDetailsViewModelFactory::class)
class RouteDetailsViewModel @AssistedInject constructor(
    @Assisted val routeId: String,
    private val getRouteDetailsUseCase: GetRouteDetailsUseCase
): ViewModel() {

    @AssistedFactory
    interface RouteDetailsViewModelFactory {
        fun create(routeId: String): RouteDetailsViewModel
    }

    private val _routeDetails = MutableStateFlow<UiState<RouteDetails>>(UiState.Empty())
    val routeDetails = _routeDetails.asStateFlow()
        .onStart {
            fetchRouteDetails(routeId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiState.Empty()
        )

    private fun fetchRouteDetails(routeId: String) = viewModelScope.launch {
        _routeDetails.value = UiState.Loading()
        getRouteDetailsUseCase(routeId).fold(
            onSuccess = {
                _routeDetails.value = UiState.Success(it)
            },
            onFailure = {
                _routeDetails.value = UiState.Error(message = it.message ?: "An error occurred")
            }
        )
    }

}