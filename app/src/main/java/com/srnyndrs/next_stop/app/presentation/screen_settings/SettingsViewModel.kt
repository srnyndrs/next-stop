package com.srnyndrs.next_stop.app.presentation.screen_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srnyndrs.next_stop.app.domain.usecase.user_preferences.GetIntPreferenceByKeyUseCase
import com.srnyndrs.next_stop.app.domain.usecase.user_preferences.SetIntPreferenceByKeyUseCase
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getIntPreferenceByKeyUseCase: GetIntPreferenceByKeyUseCase,
    private val setIntPreferenceByKeyUseCase: SetIntPreferenceByKeyUseCase
): ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()
        .onStart {
            fetchIntPreferences()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SettingsState()
        )

    fun setIntPreference(key: PreferenceKey, value: Int) = viewModelScope.launch {
        setIntPreferenceByKeyUseCase(key, value)
        fetchIntPreferences()
    }

    private fun fetchIntPreferences() = viewModelScope.launch {
        try {
            val intPreferences = PreferenceKey.entries.associateWith { key ->
                getIntPreferenceByKeyUseCase(key)
            }
            _settingsState.value = _settingsState.value.copy(
                intPreferences = UiState.Success(data = intPreferences)
            )
        } catch (e: Exception) {
            _settingsState.value = _settingsState.value.copy(
                intPreferences = UiState.Error(message = e.message ?: "An error occurred")
            )
        }
    }

}