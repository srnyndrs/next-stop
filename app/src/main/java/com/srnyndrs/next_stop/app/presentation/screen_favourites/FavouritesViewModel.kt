package com.srnyndrs.next_stop.app.presentation.screen_favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.route.GetFavouriteRoutesUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.single.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouriteRoutesUseCase: GetFavouriteRoutesUseCase
): ViewModel(){

    private val _routeState = MutableStateFlow<UiState<List<Route>>>(UiState.Loading())
    val routeState = _routeState.asStateFlow()
        .onStart { fetchFavouriteRoutes() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            UiState.Loading()
        )

    private fun fetchFavouriteRoutes() = viewModelScope.launch {
        getFavouriteRoutesUseCase().let { routes ->
            _routeState.value = UiState.Success(data = routes)
        }
    }

}