package com.srnyndrs.next_stop.app.presentation.screen_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.screen_settings.components.PreferenceDialog
import com.srnyndrs.next_stop.app.presentation.screen_settings.components.PreferenceList
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.isNumber
import com.srnyndrs.next_stop.app.presentation.util.validate
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel
) {

    val state by viewModel.settingsState.collectAsStateWithLifecycle()
    var selectedPreference by rememberSaveable { mutableStateOf<Pair<PreferenceKey, Int>?>(null) }

    val isValidSelectedPreference: (String) -> Boolean = { value: String ->
        selectedPreference?.let { (key, _) ->
            key.validate(value)
        } ?: true
    }

    Box(
        modifier = Modifier.then(modifier),
    ) {
        // Preferences
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            // Title
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge
            )
            // Preferences
            UiStateContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = state.intPreferences
            ) { intPreferences ->
                PreferenceList(
                    modifier = Modifier.fillMaxSize(),
                    preferences = intPreferences
                ) { preferenceKey, value ->
                    selectedPreference = preferenceKey to value
                }
            }
        }
        // Dialog
        if(selectedPreference != null) {
            PreferenceDialog(
                preferenceKey = selectedPreference!!.first,
                value = selectedPreference!!.second,
                onValidate = isValidSelectedPreference,
                onDismiss = { selectedPreference = null }
            ) { newValue ->
                // Set preference
                viewModel.setIntPreference(selectedPreference!!.first, newValue)
                // Hide dialog
                selectedPreference = null
            }
        }
    }
}