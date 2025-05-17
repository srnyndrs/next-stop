package com.srnyndrs.next_stop.app.presentation.screen_home

sealed interface HomeScreenEvent {
    data object GetNearbyDeparturesEvent : HomeScreenEvent
}