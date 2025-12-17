package com.alirsyad.app.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionManager {
    fun checkPermission(activity: Activity): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Log.e("debug", "debug: ${Environment.isExternalStorageManager()}")
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)
            result == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermission(activity: Activity, launcher : ActivityResultLauncher<Intent>) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(READ_EXTERNAL_STORAGE),
                400
            )
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(READ_EXTERNAL_STORAGE),
                400
            )
        }
    }
}