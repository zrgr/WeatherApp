package com.example.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.WeatherViewModel
import com.example.weather.databinding.FragmentLocationBinding


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import java.util.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.provider.Settings
import androidx.core.content.getSystemService


class LocationFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentLocationBinding

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        binding.getCurrentLocation.setOnClickListener {
            //setLocation(binding.location.text.toString())
            getLocation()
        }

        Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    private fun setLocation(location: String) {
        findNavController()
            .navigate(R.id.action_locationFragment_to_jacketFragment)

    }

    //Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        showLocationToast(location)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun showLocationToast(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val list: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        Toast.makeText(requireContext(), "Latitude: ${location.latitude} Longitude: ${location.longitude}", Toast.LENGTH_SHORT).show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

}