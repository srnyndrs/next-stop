package com.srnyndrs.next_stop.app.presentation.components.stop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.sample.StopPreviewParameterProvider
import com.srnyndrs.next_stop.app.presentation.components.stop.marker.MarkerContent
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

@Composable
fun StopHeader(
    modifier: Modifier = Modifier,
    stop: Stop
) {
    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Direction
        MarkerContent(
            selected = false,
            colors = stop.colors,
            rotationDegree = stop.direction ?: 0F
        )
        // Stop name
        Text(
            text = stop.stopName,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@PreviewLightDark
@Composable
fun StopHeaderPreview(
    @PreviewParameter(StopPreviewParameterProvider::class) stop: Stop
) {
    NextStopTheme {
        Surface {
            StopHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 3.dp),
                stop = stop
            )
        }
    }
}