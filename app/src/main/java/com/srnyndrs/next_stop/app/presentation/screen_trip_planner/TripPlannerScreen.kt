package com.srnyndrs.next_stop.app.presentation.screen_trip_planner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.srnyndrs.next_stop.app.presentation.common.LoadingType
import com.srnyndrs.next_stop.app.presentation.common.UiState
import com.srnyndrs.next_stop.app.presentation.components.TabRowComponent
import com.srnyndrs.next_stop.app.presentation.components.UiStateContainer
import com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components.TripPlanContent
import com.srnyndrs.next_stop.app.presentation.screen_trip_planner.components.TripPlanForm
import com.srnyndrs.next_stop.app.presentation.util.formatDateWithPattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripPlannerScreen(
    modifier: Modifier = Modifier,
    viewModel: TripPlannerViewModel
) {

    val state by viewModel.tripPlanState.collectAsStateWithLifecycle()

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        confirmValueChange = { newSheetValue ->
            !(state !is UiState.Empty && newSheetValue == SheetValue.Hidden)
        },
        skipHiddenState = false
    )
    val scaffoldSheetState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    LaunchedEffect(state) {
        when(state) {
            is UiState.Success, is UiState.Error -> bottomSheetState.partialExpand()
            else -> bottomSheetState.hide()
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.then(modifier),
        sheetPeekHeight = 148.dp,
        sheetShape = when(scaffoldSheetState.bottomSheetState.currentValue) {
            SheetValue.Expanded -> RectangleShape
            else -> BottomSheetDefaults.ExpandedShape
        },
        scaffoldState = scaffoldSheetState,
        sheetContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        sheetContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Trip plans",
                    style = MaterialTheme.typography.headlineMedium
                )
                if(state !is UiState.Empty) {
                    UiStateContainer(
                        modifier = Modifier.fillMaxSize(),
                        uiState = state,
                        loadingType = LoadingType.LINEAR
                    ) { data ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            data.plans?.let { tripPlans ->
                                TabRowComponent(
                                    modifier = Modifier.fillMaxSize(),
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                    tabs = tripPlans.indices.toList().map { index -> "Option ${index+1}" },
                                    contentScreens = tripPlans.map { tripPlan ->
                                        {
                                            TripPlanContent(
                                                modifier = Modifier.fillMaxSize(),
                                                tripPlan = tripPlan
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        TripPlanForm(
            modifier = Modifier.fillMaxSize()
        ) { formState ->
            viewModel.fetchTripPlan(
                date = formState.date.formatDateWithPattern("yyyy-MM-dd")!!,
                time = "%02d:%02d".format(formState.hour, formState.minutes),
                isArriveBy = formState.isArriveBy
            )
        }
    }

}