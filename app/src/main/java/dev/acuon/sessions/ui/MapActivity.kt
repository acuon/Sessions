package dev.acuon.sessions.ui

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dev.acuon.sessions.BuildConfig
import dev.acuon.sessions.R
import dev.acuon.sessions.databinding.ActivityMapBinding
import dev.acuon.sessions.utils.ActivityUtils
import dev.acuon.sessions.utils.Constants
import dev.acuon.sessions.utils.PermissionUtils

@RequiresApi(Build.VERSION_CODES.M)
class MapActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private var defaultLocation: LatLng? = null
    private var currentLocation: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null

    private lateinit var locationRequest: LocationRequest

    companion object {
        private const val GPS_REQUEST_CODE = 3
        private const val PLACES_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpMap()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Places.initialize(applicationContext, BuildConfig.API_KEY)

        locationRequest = LocationRequest.create()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 2000
        }

        binding.apply {
            fabCurrentLocation.setOnClickListener {
                if (!PermissionUtils.checkPermission(GPS_REQUEST_CODE, applicationContext)) {
                    PermissionUtils.requestPermission(GPS_REQUEST_CODE, this@MapActivity)
                } else if (currentLocation != null/* && currentLocation != LatLng(-34.0, 151.0)*/) {
                    addMarkerOnMap(currentLocation!!)
                } else {
                    getCurrentLocation()
                }
            }
            editText.apply {
                isFocusable = false
                setOnClickListener {
                    val fieldList: List<Place.Field> =
                        arrayListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
                    val intentBuilder =
                        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                            .build(this@MapActivity)
                    startActivityForResult(intentBuilder, PLACES_REQUEST_CODE)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GPS_REQUEST_CODE) {
            if (grantResults.isNotEmpty()) {
                val fineLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (fineLocationAccepted) {
                    toast("Permission Granted, now you can access the location.")
                    if (isGPSEnabled()) {
                        getCurrentLocation()
                    } else {
                        turnOnGPS()
                    }
                } else {
                    toast("Permission Denied, You cannot access location.")
                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                        PermissionUtils.showMessageOKCancel(
                            "You need to allow access to camera permissions, to capture image using camera",
                            DialogInterface.OnClickListener { dialog, which ->
                                requestPermissions(arrayOf(ACCESS_FINE_LOCATION), GPS_REQUEST_CODE)
                            },
                            this
                        )
                    } else {
                        showRotationalDialogForPermission()
                    }
                }
            }
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage(
                Constants.PERMISSION_DENIED_MESSAGE
            )
            .setPositiveButton("Go TO SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts(Constants.PACKAGE, packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACES_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
                binding.apply {
                    editText.setText(place!!.address)
//                    tv1.text = String.format("Locality name : %s", place.name)
//                    tv2.text = "${place.latLng}"
                    place.latLng?.let { addMarkerOnMap(it) }
                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(this, "${status.statusMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this@MapActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled()) {
//                    currentLocation = getLastKnownLocation()
//                    toast(currentLocation!!.latitude.toString()+"-:-"+currentLocation!!.longitude)
//                    addMarkerOnMap(currentLocation!!)
                    LocationServices.getFusedLocationProviderClient(this@MapActivity)
                        .requestLocationUpdates(locationRequest, object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                LocationServices.getFusedLocationProviderClient(this@MapActivity)
                                    .removeLocationUpdates(this)
                                if (locationResult != null && locationResult.locations.size > 0) {
                                    val index = locationResult.locations.size - 1
                                    val latitude = locationResult.locations[index].latitude
                                    val longitude = locationResult.locations[index].longitude
//                                    binding.location.text =
//                                        "Latitude: $latitude\nLongitude: $longitude"
                                    currentLocation = LatLng(latitude, longitude)
                                    addMarkerOnMap(currentLocation!!)
                                }
                            }
                        }, Looper.getMainLooper())
                } else {
                    turnOnGPS()
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }
    private fun getLastKnownLocation(): LatLng {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsLoc: Location? = null
        var netLoc: Location? = null
        var finalLoc: Location? = null
        if(PermissionUtils.checkPermission(GPS_REQUEST_CODE, applicationContext)) {
            gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }
        if (gpsLoc != null && netLoc != null) {
            if (gpsLoc.accuracy > netLoc.accuracy)
                finalLoc = netLoc
            else
                finalLoc = gpsLoc
        } else {
            if (gpsLoc != null) {
                finalLoc = gpsLoc
            } else if (netLoc != null) {
                finalLoc = netLoc
            }
        }
        var latLng = LatLng(-34.0, 151.0)
        if(finalLoc != null) {
            LatLng(finalLoc.latitude, finalLoc.longitude).also { latLng = it }
        }
        return latLng
//        var latLng: LatLng? = null
//        if(PermissionUtils.checkPermission(GPS_REQUEST_CODE, applicationContext)) {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location : Location? ->
//                    latLng = location?.let { LatLng(it.latitude, location.longitude) }
//                }
//        }
//        if(latLng != null) {
//            return latLng as LatLng
//        }
//        return LatLng(-34.0, 151.0)
    }

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
            applicationContext
        ).checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                toast(Constants.GPS_TURNED_ON)
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(
                            this@MapActivity,
                            GPS_REQUEST_CODE
                        )
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        })
    }

    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        var isEnabled = false
        if (locationManager == null) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isEnabled
    }

    private fun setUpMap() {
        // Default location is set as sydney
        defaultLocation = LatLng(-34.0, 151.0)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.addMarker(MarkerOptions().position(defaultLocation!!).title(Constants.SYDNEY))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation!!))
        val cameraPosition = CameraPosition.Builder()
            .target(defaultLocation!!)
            .zoom(10f).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        mMap.setOnMapClickListener { latLng -> // Creating a marker
            addMarkerOnMap(latLng)
        }
    }

    private fun addMarkerOnMap(latLng: LatLng) {
        val markerOptions = MarkerOptions()

        // Setting the position for the marker
        markerOptions.position(latLng)
        markerOptions.title("${latLng.latitude} : ${latLng.longitude}")

        // Clears the previously touched position or current position
        mMap.clear()
        mMap.addMarker(
            MarkerOptions().position(latLng).title(Constants.CURRENT_LOCATION)
        )!!

        // Animating to the touched position or current position
        if (mMap.cameraPosition.zoom < 15f) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(15f).build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }

        // Placing a marker on the touched position or current position
        mMap.addMarker(markerOptions)
    }

    private fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {
        toast("from location changed")
        addMarkerOnMap(LatLng(location.latitude, location.longitude))
    }
}