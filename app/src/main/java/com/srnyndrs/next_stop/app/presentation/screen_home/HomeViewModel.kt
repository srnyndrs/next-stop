package com.srnyndrs.next_stop.app.presentation.screen_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.GetNearbyDeparturesUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.combined.NearbyDepartures
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearbyDeparturesUseCase: GetNearbyDeparturesUseCase
): ViewModel() {

    private val _departures = MutableStateFlow<UiState<NearbyDepartures>>(UiState.Loading())
    val departures = _departures.asStateFlow()
        .onStart { fetchDepartures() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading()
        )

    private fun fetchDepartures() = viewModelScope.launch(Dispatchers.IO) {
        _departures.value = UiState.Loading()
        getNearbyDeparturesUseCase().fold(
            onSuccess = {
                _departures.value = UiState.Success(data = it)
            },
            onFailure = {
                _departures.value = UiState.Error(message = it.message ?: "An error occurred")
            }
        )
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.GetNearbyDeparturesEvent -> fetchDepartures()
        }
    }
}