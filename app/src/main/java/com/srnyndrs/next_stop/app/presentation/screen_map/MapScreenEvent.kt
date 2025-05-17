package com.srnyndrs.next_stop.app.presentation.screen_map

import com.google.android.gms.maps.model.LatLngBounds
import com.srnyndrs.next_stop.shared.domain.model.single.Stop

sealed interface MapScreenEvent {
    data class MapBoundsChangeEvent(val bounds: LatLngBounds): MapScreenEvent
    data class MarkerClickEvent(val stop: Stop): MapScreenEvent
    data class GetStopDetailsEvent(val stopId: String): MapScreenEvent
    data object MapClickEvent: MapScreenEvent
}