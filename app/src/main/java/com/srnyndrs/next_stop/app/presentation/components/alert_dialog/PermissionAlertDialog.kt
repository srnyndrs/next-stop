package com.srnyndrs.next_stop.app.presentation.components.alert_dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.srnyndrs.next_stop.app.R
import com.srnyndrs.next_stop.app.domain.util.PermissionsUtil.getTextToShowGivenPermissions
import com.srnyndrs.next_stop.app.presentation.ui.theme.NextStopTheme

@Composable
fun PermissionAlertDialog(
    modifier: Modifier = Modifier,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.button_label_request_permission))
            }
        },
        text = {
            Text(text = text)
        }
    )
}

@ExperimentalPermissionsApi
class MultiplePermissionsStatePreview : MultiplePermissionsState {

    override val allPermissionsGranted: Boolean
        get() = false

    override val permissions: List<PermissionState>
        get() = emptyList()

    override val revokedPermissions: List<PermissionState>
        get() = listOf(
            object : PermissionState {
                override val permission = "ACCESS_FINE_LOCATION"
                override val status = PermissionStatus.Granted
                override fun launchPermissionRequest() = Unit
            },
            object : PermissionState {
                override val permission = "ACCESS_COARSE_LOCATION"
                override val status = PermissionStatus.Granted
                override fun launchPermissionRequest() = Unit
            }
        )

    override val shouldShowRationale: Boolean
        get() = true

    override fun launchMultiplePermissionRequest() {
        // do nothing
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@PreviewLightDark
@Composable
fun PermissionAlertDialogPreview() {
    val locationPermissions = MultiplePermissionsStatePreview()
    NextStopTheme {
        Surface {
            PermissionAlertDialog(
                text = getTextToShowGivenPermissions(
                    permissions = locationPermissions.revokedPermissions,
                    context = LocalContext.current,
                    shouldShowRationale = locationPermissions.shouldShowRationale
                ),
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}