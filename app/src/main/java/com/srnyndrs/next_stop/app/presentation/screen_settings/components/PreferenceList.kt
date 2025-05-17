package com.srnyndrs.next_stop.app.presentation.screen_settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey

@Composable
fun PreferenceList(
    modifier: Modifier = Modifier,
    preferences: Map<PreferenceKey, Int>,
    onPreferenceChange: (PreferenceKey, Int) -> Unit,
) {

    val list = preferences.flatMap { (key, value) -> listOf(key to value) }

    LazyColumn(
        modifier = Modifier.then(modifier),
        //verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(list) { (preferenceKey, value) ->
            PreferenceRow(
                modifier = Modifier.fillMaxWidth().requiredHeight(56.dp),
                preferenceKey = preferenceKey,
                value = value,
                onPreferenceChange = onPreferenceChange
            )
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 12.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PreferenceListPreview() {
    NextStopTheme {
        Surface {
            PreferenceList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                preferences = mapOf(
                    PreferenceKey.TIME to 30,
                    PreferenceKey.RADIUS to 500
                )
            ) { _,_ -> }
        }
    }
}