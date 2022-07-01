package dev.acuon.sessions.utils

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.acuon.sessions.utils.Constants.CAMERA_REQUEST_CODE
import dev.acuon.sessions.utils.Constants.GPS_REQUEST_CODE
import dev.acuon.sessions.utils.Constants.STORAGE_REQUEST_CODE

object PermissionUtils {
    fun checkPermission(requestCode: Int, applicationContext: Context): Boolean {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                return ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            }
            STORAGE_REQUEST_CODE -> {
                return ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
            GPS_REQUEST_CODE -> {
                return ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    fun requestPermission(requestCode: Int, context: Context) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_REQUEST_CODE
                )
            }
            STORAGE_REQUEST_CODE -> {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_REQUEST_CODE
                )
            }
            GPS_REQUEST_CODE -> {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    GPS_REQUEST_CODE
                )
            }
        }
    }

    fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener,
        context: Context
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }
}