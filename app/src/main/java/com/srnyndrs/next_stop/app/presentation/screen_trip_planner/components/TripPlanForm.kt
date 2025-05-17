package com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.anhaki.picktime.PickHourMinute
import com.anhaki.picktime.utils.PickTimeFocusIndicator
import com.anhaki.picktime.utils.PickTimeTextStyle
import com.srnyndrs.next_stop.app.presentation.components.SingleChoiceSegmentedButton
import com.srnyndrs.next_stop.app.presentation.components.picker.DatePickerRow
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme
import com.srnyndrs.next_stop.app.presentation.util.currentDateInMillis
import com.srnyndrs.next_stop.app.presentation.util.formatDateWithPattern

@Composable
fun TripPlanForm(
    modifier: Modifier = Modifier,
    onSubmit: (TripPlanFormState) -> Unit
) {

    val formState = remember { mutableStateOf(
        TripPlanFormState(
            date = currentDateInMillis(),
            hour = 12,
            minutes = 0
        )
    ) }

    var selectedArriveType by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Trip planner",
            style = MaterialTheme.typography.headlineMedium
        )

        SingleChoiceSegmentedButton(
            modifier = Modifier.fillMaxWidth(),
            selectedIndex = selectedArriveType,
            options = listOf("Departure", "Arrival")
        ) {
            selectedArriveType = it
            formState.value = formState.value.copy(isArriveBy = (it == 1))
        }

        DatePickerRow(
            modifier = Modifier.fillMaxWidth(),
            value = formState.value.date
        ) { newDate ->
            formState.value = formState.value.copy(date = newDate)
        }

        PickHourMinute(
            containerColor = MaterialTheme.colorScheme.surface,
            initialHour = formState.value.hour,
            onHourChange = {
                formState.value = formState.value.copy(hour = it)
            },
            initialMinute = formState.value.minutes,
            onMinuteChange = {
                formState.value = formState.value.copy(minutes = it)
            },
            focusIndicator = PickTimeFocusIndicator(
                enabled = true,
                widthFull = false,
                background = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ),
            selectedTextStyle = PickTimeTextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            ),
            unselectedTextStyle = PickTimeTextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                if(formState.value.isValid()) {
                    onSubmit(formState.value)
                }
            }
        ) {
            Text(
                text = "Plan Trip"
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TripPlanFormPreview() {
    NextStopTheme {
        Surface {
            TripPlanForm(
                modifier = Modifier.fillMaxSize()
            ) {  }
        }
    }
}