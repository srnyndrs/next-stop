package com.srnyndrs.next_stop.app.presentation.screen_settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.validate
import com.srnyndrs.next_stop.shared.domain.model.single.PreferenceKey
import java.util.Locale

@Composable
fun PreferenceDialog(
    preferenceKey: PreferenceKey,
    value: Int,
    onValidate: (String) -> Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {

    var textState by rememberSaveable { mutableStateOf("$value") }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Edit preference", // TODO
                    style = MaterialTheme.typography.headlineSmall
                )
                // Preference
                TextField(
                    value = textState,
                    onValueChange = { newValue ->
                        textState = newValue
                    },
                    label = {
                        Text(
                            text = preferenceKey.label,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    supportingText = {
                        Text(
                            text = preferenceKey.description,
                            style = MaterialTheme.typography.labelMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    suffix = {
                        Text(text = preferenceKey.metric)
                    },
                    isError = !onValidate(textState),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Cancel button
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    ) {
                        Text(
                            text = "Cancel", // TODO
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    // Confirm Button
                    Button(
                        onClick = {
                            if(onValidate(textState)) {
                                onConfirm(textState.toInt())
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    ) {
                        Text(
                            text = "Confirm", // TODO
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreferenceDialogPreview() {
    NextStopTheme {
        Surface {
            PreferenceDialog(
                preferenceKey = PreferenceKey.TIME,
                value = 60,
                onValidate = { true },
                onDismiss = {}
            ) {

            }
        }
    }
}