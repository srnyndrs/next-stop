package com.srnyndrs.next_stop.app.presentation.screen_map

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.single.Stop
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule

data class GoogleMapState(
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(LatLng(47.497913, 19.040236), 10f),
    val isLoading: Boolean = false,
    val stops: List<Stop> = emptyList(),
    val selectedStop: Stop? = null,
    val stopDetails: UiState<StopDetails> = UiState.Empty(),
    val stopSchedule: UiState<StopSchedule> = UiState.Empty()
)