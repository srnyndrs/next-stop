package com.srnyndrs.next_stop.app.presentation.components.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.ArrowRight
import com.composables.icons.lucide.Lucide
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.toDateOnlyMillis
import com.srnyndrs.next_stop.app.presentation.util.toFormattedDate

@Composable
fun DatePickerRow(
    modifier: Modifier = Modifier,
    value: Long,
    onValueChange: (Long) -> Unit
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onValueChange(value - 86400000)
            }
        ) {
            Icon(
                imageVector = Lucide.ArrowLeft,
                contentDescription = null // TODO
            )
        }
        Text(
            text = value.toFormattedDate(),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(
            onClick = {
                onValueChange(value + 86400000)
            }
        ) {
            Icon(
                imageVector = Lucide.ArrowRight,
                contentDescription = null // TODO
            )
        }
    }

}

@PreviewLightDark
@Composable
fun DatePickerRowPreview() {
    NextStopTheme {
        Surface {
            var dateState by remember { mutableLongStateOf(System.currentTimeMillis().toDateOnlyMillis()) }
            DatePickerRow(
                modifier = Modifier.fillMaxWidth(),
                value = dateState,
                onValueChange = {
                    dateState = it
                }
            )
        }
    }
}