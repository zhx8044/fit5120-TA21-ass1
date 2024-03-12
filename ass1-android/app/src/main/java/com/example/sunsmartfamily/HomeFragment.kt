package com.example.sunsmartfamily

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Call
import android.widget.Button
import androidx.navigation.fragment.findNavController
import android.util.Patterns;







class HomeFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        locationTextView = view.findViewById(R.id.home_locationText)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Initialize the location request
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {

                    // 现有代码：更新位置UI
                    updateLocationUI(location)

                    // 新增代码：获取紫外线强度
                    fetchUVIndex(location.latitude, location.longitude)
                }
            }
        }

        checkLocationPermission()


        // Setup the "SET LOCATION" button click listener
        val setLocationButton: Button = view.findViewById(R.id.home_setLocationButton)
        setLocationButton.setOnClickListener {
            // Perform a FragmentTransaction to replace the HomeFragment with FragmentA
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.content_frame, FragmentA())
                addToBackStack(null)  // Optional: Add transaction to the back stack
                commit()
            }
        }

        // Setup the "VIEW REMINDER" button click listener
        val viewReminderButton: Button = view.findViewById(R.id.home_viewReminderButton)
        viewReminderButton.setOnClickListener {
            // Perform a FragmentTransaction to replace the HomeFragment with FragmentA
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.content_frame, FragmentB())
                addToBackStack(null)  // Optional: Add transaction to the back stack
                commit()
            }
        }



        return view
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            getLocationUpdates()
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    private fun updateLocationUI(location: Location) {
        locationTextView.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
    }

    // 得到UVIndex
    private fun fetchUVIndex(latitude: Double, longitude: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherApiService::class.java)
        val call = service.getUVIndex(latitude, longitude, "b2c4424d55a9bc355355a8a749fc54c2")

        call.enqueue(object : Callback<UVIndexResponse> {
            override fun onResponse(call: Call<UVIndexResponse>, response: Response<UVIndexResponse>) {
                if (response.isSuccessful) {
                    // 获取紫外线指数值
                    val uvIndexValue = response.body()?.value
                    // 获取紫外线指数的 TextView
                    val uvIndexTextView: TextView = view?.findViewById(R.id.uvIndexTextView) ?: return
                    // 在UI上显示紫外线指数
                    activity?.runOnUiThread {
                        uvIndexTextView.text = "UV Index: $uvIndexValue"
                    }
                } else {
                    // API响应不成功，处理错误
                    // 显示错误信息或进行其他适当的错误处理
                }
            }

            override fun onFailure(call: Call<UVIndexResponse>, t: Throwable) {
                // 处理错误
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocationUpdates()
                } else {
                    Toast.makeText(activity, "Permission denied to access location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}
