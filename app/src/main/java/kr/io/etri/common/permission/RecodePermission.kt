package kr.io.etri.common.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale

/**
 * etri
 * Created by Naedong
 * Date: 2023-10-23(023)
 * Time: 오전 11:07
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandlePermissionAction(
    permissionState : MultiplePermissionsState,
    showPermissionDialog: MutableState<Boolean>
){
    if(showPermissionDialog.value){
        CheckPermissions(permissionState = permissionState
            , onPermissionResult = { if(it) showPermissionDialog.value = false },
            showPermissionState = showPermissionDialog
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(
    permissionState: MultiplePermissionsState,
    onPermissionResult : (Boolean) -> Unit,
    showPermissionState: MutableState<Boolean>
){
    val context = LocalContext.current

    val permissionDescriptionProviderMap = PermissionMap()

    when {
        permissionState.allPermissionsGranted -> {
            onPermissionResult(true)
        }
        else -> {
            onPermissionResult(false)
            RevokedPermissionsDialog(
                permissionState = permissionState,
                permissionDesciptionProviderMap = permissionDescriptionProviderMap,
                context = context,
                showPermissionDialog = showPermissionState
            )

        }
    }

}

private fun PermissionMap() =
    mapOf(
        Manifest.permission.RECORD_AUDIO to RecodeAudioPermissionDescriptionProvider()
    )

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RevokedPermissionsDialog(
    permissionState : MultiplePermissionsState,
    permissionDesciptionProviderMap : Map<String, PermissionDescriptionProvider>,
    context: Context,
    showPermissionDialog : MutableState<Boolean>
){
    val lastRevokedPermission = permissionState.revokedPermissions.lastOrNull()

    lastRevokedPermission?.let { permission ->
        val descriptionProvider = permissionDesciptionProviderMap[permission.permission] ?: return@let

        ShowPermissionDialog(
            permissionState = permissionState,
            permissionStatus = permission.status,
            descriptionProvider = descriptionProvider,
            context = context,
            showPermissionDialog = showPermissionDialog
        )

    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowPermissionDialog(
    permissionState: MultiplePermissionsState,
    permissionStatus : PermissionStatus,
    descriptionProvider: PermissionDescriptionProvider,
    context: Context,
    showPermissionDialog: MutableState<Boolean>,
){
    PermissionDialog(
        permissionDescriptionProvider = descriptionProvider,
        isPermanentlyDeclined = !permissionStatus.shouldShowRationale,
        onDismiss = { showPermissionDialog.value = false },
        onOkClick = { permissionState.launchMultiplePermissionRequest() },
        onGoToAppSettingsClick = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    )

}

@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionDescriptionProvider: PermissionDescriptionProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        confirmButton = {
            Button(
                onClick = {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOkClick()
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "확인",
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(
                    modifier = Modifier.padding(6.dp),
                    text = "취소",
                )
            }
        },
        title = {
            Text(
                text = permissionDescriptionProvider.getTitle(
                    context
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        text = {
            Text(
                text = permissionDescriptionProvider.getDescription(
                    context,
                    isPermanentlyDeclined = isPermanentlyDeclined
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    )
}
