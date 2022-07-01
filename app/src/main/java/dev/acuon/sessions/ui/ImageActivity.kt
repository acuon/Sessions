package dev.acuon.sessions.ui

import android.Manifest.permission.*
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import coil.load
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityImageBinding
import dev.acuon.sessions.utils.ActivityUtils
import dev.acuon.sessions.utils.Constants
import dev.acuon.sessions.utils.PermissionUtils

@RequiresApi(Build.VERSION_CODES.M)
class ImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImageBinding

    companion object {
        private const val CAMERA_REQUEST_CODE = 1
        private const val STORAGE_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnCamera.setOnClickListener(this@ImageActivity)
            btnGallery.setOnClickListener(this@ImageActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCamera -> {
                if (!PermissionUtils.checkPermission(CAMERA_REQUEST_CODE, applicationContext)) {
                    PermissionUtils.requestPermission(CAMERA_REQUEST_CODE, this@ImageActivity);
                } else {
                    toast("Permission already granted.")
                    camera()
                }
            }
            R.id.btnGallery -> {
                if (!PermissionUtils.checkPermission(STORAGE_REQUEST_CODE, applicationContext)) {
                    PermissionUtils.requestPermission(STORAGE_REQUEST_CODE, this@ImageActivity)
                } else {
                    toast("Permission already granted.")
                    gallery()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.popup_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        ActivityUtils.intent(item.itemId, this)
        return true
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = Constants.IMAGE_TYPE
        startActivityForResult(intent, STORAGE_REQUEST_CODE)
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get(Constants.DATA) as Bitmap
                    //we are using coroutine image loader (coil)
                    binding.imageView.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
//                        transformations(CircleCropTransformation())
                    }
                }
                STORAGE_REQUEST_CODE -> {
                    binding.imageView.load(data?.data) {
                        crossfade(true)
                        crossfade(1000)
//                        transformations(CircleCropTransformation())
                    }

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted) {
                        toast("Permission Granted, Now you can access camera.")
                        camera()
                    } else {
                        toast("Permission Denied, You cannot access camera.")
                        if (shouldShowRequestPermissionRationale(CAMERA)) {
                            PermissionUtils.showMessageOKCancel(
                                "You need to allow access to camera permissions, to capture image using camera",
                                DialogInterface.OnClickListener { dialog, which ->
                                    requestPermissions(arrayOf(CAMERA), CAMERA_REQUEST_CODE)
                                },
                                this
                            )
                        } else {
                        }
                    }
                }
            }
            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    val storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (storageAccepted) {
                        toast("Permission Granted, Now you can access storage.")
                        gallery()
                    } else {
                        toast("Permission Denied, You cannot access storage.")
                        if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                            PermissionUtils.showMessageOKCancel(
                                "You need to allow access to storage permission, to select image from gallery",
                                DialogInterface.OnClickListener { dialog, which ->
                                    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE)
                                },
                                this
                            )
                        } else {
                        }
                    }
                }
            }
        }
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}