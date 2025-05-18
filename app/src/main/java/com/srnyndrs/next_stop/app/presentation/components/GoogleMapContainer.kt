package com.srnyndrs.next_stop.app.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.srnyndrs.next_stop.app.R

@Composable
fun GoogleMapContainer(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    onMapClick: () -> Unit,
    content: @Composable @GoogleMapComposable () -> Unit
) {

    val context = LocalContext.current

    val mapProperties = MapProperties(
        isMyLocationEnabled = true,
        maxZoomPreference = 18f,
        minZoomPreference = 12f,
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    )

    val mapUiSettings = MapUiSettings(
        myLocationButtonEnabled = false,
        compassEnabled = false,
        mapToolbarEnabled = false,
        zoomControlsEnabled = false
    )

    GoogleMap(
        modifier = Modifier.then(modifier),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings,
        onMapClick = { onMapClick() }
    ) {
        content()
    }
}