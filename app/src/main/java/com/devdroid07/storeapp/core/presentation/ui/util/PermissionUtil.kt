package com.devdroid07.storeapp.core.presentation.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

fun ComponentActivity.shouldShowCamaraPermissionRationale(): Boolean {
    return shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
}


private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasCamaraPermission(): Boolean{
    return hasPermission(Manifest.permission.CAMERA)
}