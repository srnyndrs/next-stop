package com.srnyndrs.next_stop.app.presentation.screen_settings.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey

@Composable
fun PreferenceRow(
    modifier: Modifier = Modifier,
    preferenceKey: PreferenceKey,
    value: Int,
    onPreferenceChange: (PreferenceKey, Int) -> Unit
) {
    Row (
        modifier = Modifier.then(modifier)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.7f)
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = preferenceKey.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = preferenceKey.description,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.3f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Column (
                    modifier = Modifier
                        .weight(0.4f)
                        .clickable {
                            onPreferenceChange(preferenceKey, value)
                        }
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$value",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.requiredWidth(6.dp))
                Text(
                    modifier = Modifier,
                    text = preferenceKey.metric,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreferenceRowPreview() {
    NextStopTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            PreferenceRow(
                modifier = Modifier.fillMaxWidth().requiredHeight(56.dp),
                preferenceKey = PreferenceKey.TIME,
                value = 60
            ) { _, _ -> }
        }
    }
}