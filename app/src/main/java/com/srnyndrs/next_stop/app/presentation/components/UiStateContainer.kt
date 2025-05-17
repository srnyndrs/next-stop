package com.srnyndrs.next_stop.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.srnyndrs.next_stop.app.presentation.common.LoadingType
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme

@Composable
fun <T> UiStateContainer(
    modifier: Modifier = Modifier,
    uiState: UiState<T>,
    loadingType: LoadingType = LoadingType.CIRCULAR,
    content: @Composable (T) -> Unit
) {
    Box(
        modifier = Modifier.then(modifier),
        contentAlignment = Alignment.TopCenter
    ) {
        when(uiState) {
            is UiState.Empty -> {}
            is UiState.Error -> {
                val errorMessage = uiState.message!!
                Row(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.errorContainer,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is UiState.Loading -> {
                when(loadingType) {
                    LoadingType.CIRCULAR -> CircularProgressIndicator()
                    LoadingType.LINEAR -> LinearProgressIndicator()
                }
            }
            is UiState.Success -> {
                uiState.data?.let { content(it) }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun UiStateContainerSuccessPreview() {
    NextStopTheme {
        Surface {
            UiStateContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = UiState.Success(data = "Data")
            ) { data ->
                // Content
                Text(text = data)
            }
        }
    }
}

@PreviewLightDark
@Composable
fun UiStateContainerErrorPreview() {
    NextStopTheme {
        Surface {
            UiStateContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = UiState.Error<String>(message = "Error message")
            ) { data ->
                // Content
                Text(text = data)
            }
        }
    }
}