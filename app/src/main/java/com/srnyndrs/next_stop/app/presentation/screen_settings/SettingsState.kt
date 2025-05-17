package com.srnyndrs.next_stop.app.presentation.screen_settings

import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey

data class SettingsState(
    val intPreferences: UiState<Map<PreferenceKey, Int>> = UiState.Loading(),
    val radius: UiState<Int> = UiState.Loading(),
    var time: UiState<Int> = UiState.Loading()
)
