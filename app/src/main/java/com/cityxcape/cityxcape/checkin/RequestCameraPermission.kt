package com.cityxcape.cityxcape.checkin

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat


@Composable
fun requestCameraPermission(
    context: Context,
    onGranted: () -> Unit,
    onDenied: () -> Unit = {}
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            onGranted()
        } else {
            Toast.makeText(context, "Camera Permission denied", Toast.LENGTH_SHORT).show()
            onDenied()
        }

    }

    LaunchedEffect(Unit) {
        val permission = Manifest.permission.CAMERA
        val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            launcher.launch(permission)
        } else {
            onGranted()
        }
    }

}