package com.srnyndrs.next_stop.app.presentation.screen_trip_planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.trip_plan.GetTripPlanUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.data.remote.dto.RouteType
import com.srnyndrs.next_stop.shared.domain.model.combined.TripPlanResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TripPlannerViewModel.TripPlannerViewModelFactory::class)
class TripPlannerViewModel @AssistedInject constructor(
    @Assisted private val destination: String,
    private val getTripPlanUseCase: GetTripPlanUseCase
): ViewModel() {

    @AssistedFactory
    interface TripPlannerViewModelFactory {
        fun create(destination: String): TripPlannerViewModel
    }

    private val _tripPlanState = MutableStateFlow<UiState<TripPlanResult>>(UiState.Empty())
    val tripPlanState = _tripPlanState.asStateFlow()

    fun fetchTripPlan(date: String, time: String, isArriveBy: Boolean) = viewModelScope.launch {
        _tripPlanState.value = UiState.Loading()
        getTripPlanUseCase(
            destination = destination,
            date = date,
            time = time,
            isArriveBy = isArriveBy,
            mode = listOf(
                RouteType.SUBWAY,
                RouteType.SUBURBAN_RAILWAY,
                RouteType.FERRY,
                RouteType.TRAM,
                RouteType.TROLLEYBUS,
                RouteType.BUS,
                RouteType.RAIL,
                RouteType.COACH,
                RouteType.WALK
            ) // TODO
        ).fold(
            onSuccess = {
                _tripPlanState.value = UiState.Success(data = it)
            },
            onFailure = {
                _tripPlanState.value = UiState.Error(message = it.message ?: "An error occurred")
            }
        )
    }

}