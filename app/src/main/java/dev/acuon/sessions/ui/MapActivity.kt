package dev.acuon.sessions.ui

import dev.acuon.sessions.R
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Nullable
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
import dev.acuon.sessions.databinding.ActivityMapBinding
import dev.acuon.sessions.databinding.SearchLayoutBinding
import android.widget.PopupMenu
import dev.acuon.sessions.utils.ActivityUtils


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private lateinit var searchLayoutBinding: SearchLayoutBinding
    private var defaultLocation: LatLng? = null
    private var currentLocation: LatLng? = null

    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        searchLayoutBinding =
            SearchLayoutBinding.bind(layoutInflater.inflate(R.layout.search_layout, null))
        setContentView(binding.root)
        setUpMap()

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        binding.fabCurrentLocation.setOnClickListener {
            if (currentLocation != null) {
                addMarkerOnMap(currentLocation!!)
            } else {
                getCurrentLocation()
            }
        }

        Places.initialize(applicationContext, "AIzaSyDpVcJ2ABh-ZyeIgWNml1_vrGRvksNbP7k")
        searchLayoutBinding.apply {
            editText.apply {
                isFocusable = false
                setOnClickListener {
                    val fieldList: List<Place.Field> =
                        arrayListOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
                    val intentBuilder =
                        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                            .build(this@MapActivity)
                    startActivityForResult(intentBuilder, 100)
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
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    getCurrentLocation()
                } else {
                    turnOnGPS()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                getCurrentLocation()
            }
        } else if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data)
                searchLayoutBinding.apply {
                    editText.setText(place.address)
//                    tv1.text = String.format("Locality name : %s", place.name)
//                    tv2.text = "${place.latLng}"
                    place.latLng?.let { addMarkerOnMap(it) }
                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data)
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

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
            applicationContext
        )
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Toast.makeText(this@MapActivity, "GPS is already turned on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(this@MapActivity, 2)
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
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap.addMarker(MarkerOptions().position(defaultLocation!!).title("Marker in Sydney"))
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
            MarkerOptions().position(latLng).title("Current Location")
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
}