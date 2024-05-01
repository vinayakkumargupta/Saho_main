package com.example.saho_main

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.saho_main.DashBoard.FuelFragment
import com.example.saho_main.DashBoard.HomeFragment
import com.example.saho_main.DashBoard.ProfileFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task


class HomeScreenActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 100
    private val REQUEST_CHECK_SETTINGS = 101
    private var locationFetchInProgress = false


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val fabSos: LottieAnimationView = findViewById(R.id.fabSos)
        fabSos.setOnClickListener {
            if (!locationFetchInProgress) {
                locationFetchInProgress = true
                checkLocationPermissionAndFetchLocation()
            }
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    replaceFragment(FuelFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    replaceFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        // Show the default fragment on startup
        replaceFragment(HomeFragment())
    }

    private fun checkLocationPermissionAndFetchLocation() {
        if (checkLocationPermission()) {
            checkLocationSettingsAndFetchLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun checkLocationSettingsAndFetchLocation() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // Location settings are satisfied. Fetch the location.
            fetchCurrentLocation()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied. Prompt the user to enable location services.
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    exception.startResolutionForResult(this@HomeScreenActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
            locationFetchInProgress = false
        }
    }

    private fun fetchCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    val sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val username = sharedPreferences.getString("username", "")

                    // Constructing Google Maps URL
                    val mapUrl = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"

                    // Create share intent with the Google Maps link
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "$username has called for S.O.S under ACCIDENT CASUALTY . SAHO is requesting maximum alert on the casualty and to be ready for all required emergencies\" : $mapUrl")

                    // Show chooser to select app for sharing
                    val chooser = Intent.createChooser(shareIntent, "Share Location")
                    startActivity(chooser)
                } ?: run {
                    // Location not found
                    Toast.makeText(this, "Location is Fetching Please click once Again", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }.addOnCompleteListener {
                locationFetchInProgress = false
            }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettingsAndFetchLocation()
            } else {
                locationFetchInProgress = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                // User has enabled location services. Fetch the location.
                fetchCurrentLocation()
            } else {
                // User did not enable location services. Handle this case accordingly.
                Toast.makeText(this, "Location services are required for this feature", Toast.LENGTH_SHORT).show()
                locationFetchInProgress = false
            }
        }
    }
}
