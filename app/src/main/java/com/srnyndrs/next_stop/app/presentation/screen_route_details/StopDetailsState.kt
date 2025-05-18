package com.srnyndrs.next_stop.app.presentation.screen_route_details

import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.combined.StopDetails
import com.srnyndrs.next_stop.shared.domain.model.combined.StopSchedule

data class StopDetailsState(
    val stopSchedule: UiState<StopSchedule> = UiState.Empty(),
    val stopDetails: UiState<StopDetails> = UiState.Empty(),
)
