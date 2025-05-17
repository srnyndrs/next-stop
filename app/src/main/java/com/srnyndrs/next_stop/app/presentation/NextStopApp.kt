package com.srnyndrs.next_stop.app.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.srnyndrs.next_stop.app.domain.util.PermissionsUtil.getTextToShowGivenPermissions
import com.srnyndrs.next_stop.app.presentation.components.alert_dialog.PermissionAlertDialog
import com.srnyndrs.next_stop.app.presentation.navigation.BottomNavigationBar
import com.srnyndrs.next_stop.app.presentation.navigation.NavigationGraph

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NextStopApp() {

    val context = LocalContext.current
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                onItemSelect = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { innerPadding ->
        if(locationPermissions.allPermissionsGranted) {
            NavigationGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        } else {
            PermissionAlertDialog(
                text = getTextToShowGivenPermissions(
                    locationPermissions.revokedPermissions,
                    locationPermissions.shouldShowRationale,
                    context
                ),
                onConfirm = { locationPermissions.launchMultiplePermissionRequest() },
                onDismiss = {}
            )
        }
    }
}