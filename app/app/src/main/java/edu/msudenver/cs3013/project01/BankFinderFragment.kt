package edu.msudenver.cs3013.project03

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.io.IOException

class BankFinderFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private var userMarker: Marker? = null
    private var carMarker: Marker? = null
//    val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_bank_finder, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getLastLocation(::updateMapLocationWithMarker)
                } else {
                    println("Location permission denied.")
                }
            }

        // Check if location permission is already granted
        if (hasLocationPermission()) {
            println("Location permission already granted.")
            getLastLocation(::updateMapLocationWithMarker)
        } else {
            println("Requesting location permission.")
            requestLocationPermission()
        }

        return view
    }


    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val markLocationButton: View = view.findViewById(R.id.maps_mark_location_button)
        markLocationButton.setOnClickListener {
            if (hasLocationPermission()) {
                findNearbyBanks()
            } else {
                requestLocationPermission()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (hasLocationPermission()) {
            getLastLocation(::updateMapLocationWithMarker)
        } else {
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(onLocation: (location: Location) -> Unit) {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    onLocation(it)
                }
            }
    }

    private fun updateMapLocation(location: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    private fun addMarkerAtLocation(
        location: LatLng,
        title: String,
        markerIcon: BitmapDescriptor? = null
    ) = mMap.addMarker(
        MarkerOptions()
            .title(title)
            .position(location)
            .apply {
                markerIcon?.let { icon(markerIcon) }
            }
    )

    private fun markParkedCar() {
        getLastLocation { location ->
            val userLocation = updateMapLocationWithMarker(location)
            carMarker?.remove()
            carMarker = addMarkerAtLocation(
                userLocation,
                "Your Car",
                getBitmapDescriptorFromVector(R.drawable.baseline_account_balance_24)
            )
            // Save this parking location to the ViewModel
            val parkingLocationString = "Lat: ${location.latitude}, Lng: ${location.longitude}"
//            viewModel.updateParkingLocation(parkingLocationString)

            // Move the camera to focus on the car's location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f))
        }
    }

    private fun getBitmapDescriptorFromVector(@DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor? {
        val bitmap =
            ContextCompat.getDrawable(requireContext(), vectorDrawableResourceId)?.let { vectorDrawable ->
                vectorDrawable
                    .setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

                val drawableWithTint = DrawableCompat.wrap(vectorDrawable)
                DrawableCompat.setTint(drawableWithTint, Color.RED)

                val bitmap = Bitmap.createBitmap(
                    vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawableWithTint.draw(canvas)
                bitmap
            } ?: return null
        return BitmapDescriptorFactory.fromBitmap(bitmap).also {
            bitmap.recycle()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near the user's current location.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Request location permission if not granted
        if (!hasLocationPermission()) {
            requestLocationPermission()
        } else {
            // If permission is already granted, get the last known location
            getLastLocation(::updateMapLocationWithMarker)
        }
        mMap.setOnMapClickListener { latLng ->
            // Remove the existing userMarker if present
            userMarker?.remove()

            // Add a new userMarker at the clicked location
            userMarker = addMarkerAtLocation(latLng, "You")

            // Optional: Move the camera to focus on the user's location
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    private fun updateMapLocationWithMarker(location: Location): LatLng {
        val userLocation = LatLng(location.latitude, location.longitude)
        updateMapLocation(userLocation)
        userMarker?.remove()
        userMarker = addMarkerAtLocation(userLocation, "You")
        return userLocation
    }

    private fun findNearbyBanks() {
        getLastLocation { location ->
            val userLocation = updateMapLocationWithMarker(location)

            val apiKey = "AIzaSyDeX2nOx4RliVoqUlwv4k2OY4MTk6r0IZc" // Replace this with your actual API key

            // Construct the Places API URL
            val radiusInMeters = (5 * 1609.34).toInt() // 5 miles to meters
            val placeType = "bank"
            val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&radius=$radiusInMeters&type=$placeType&key=$apiKey"

            makeNetworkRequestTo(url) { banks ->
                banks.forEach { bank ->
                    val markerIcon = getBitmapDescriptorFromVector(R.drawable.baseline_account_balance_24)
                    addMarkerAtLocation(LatLng(bank.latitude, bank.longitude), bank.name, markerIcon)
                }
                adjustMapZoomToIncludeBanks(userLocation, banks)
            }
        }
    }

    private fun makeNetworkRequestTo(url: String, onResult: (List<Bank>) -> Unit) {
        val request = okhttp3.Request.Builder()
            .url(url)
            .build()

        val client = okhttp3.OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("Error: ${e.localizedMessage}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.use { resp ->
                    if (resp.isSuccessful) {
                        val responseBody = resp.body?.string()
                        val banks = mutableListOf<Bank>()

                        if (responseBody != null) {
                            val jsonResponse = JSONObject(responseBody)
                            val banksArray = jsonResponse.getJSONArray("results")

                            for (i in 0 until banksArray.length()) {
                                val bankObject = banksArray.getJSONObject(i)
                                val bankName = bankObject.getString("name")
                                val locationObject = bankObject.getJSONObject("geometry").getJSONObject("location")
                                val lat = locationObject.getDouble("lat")
                                val lng = locationObject.getDouble("lng")

                                banks.add(Bank(name = bankName, latitude = lat, longitude = lng))
                            }
                        }

                        // Update UI on the main thread
                        activity?.runOnUiThread {
                            onResult(banks)
                        }
                    } else {
                        println("Error: ${resp.message}")
                    }
                }
            }
        })
    }

    data class Bank(val name: String, val latitude: Double, val longitude: Double)

    private fun adjustMapZoomToIncludeBanks(userLocation: LatLng, banks: List<Bank>) {
        // First, initialize the bounds with the user's location.
        var builder = LatLngBounds.builder()
        builder.include(userLocation)

        // Include each bank in the bounds.
        for (bank in banks) {
            builder.include(LatLng(bank.latitude, bank.longitude))
        }

        val bounds = builder.build()

        // Add some padding (e.g., 100 pixels) so markers aren't right on the edge.
        val padding = 100

        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(cameraUpdate)
    }

}
