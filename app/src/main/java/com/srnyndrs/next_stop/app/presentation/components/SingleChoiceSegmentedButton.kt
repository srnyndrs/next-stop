package com.srnyndrs.next_stop.app.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme

@Composable
fun SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    options: List<String>,
    onItemSelect: (Int) -> Unit
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier.then(modifier)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onItemSelect(index) },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SegmentedButtonPreview() {
    NextStopTheme {
        Surface {
            SingleChoiceSegmentedButton(
                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp, horizontal = 6.dp),
                options = listOf("Day", "Month", "Week"),
                onItemSelect = {}
            )
        }
    }
}