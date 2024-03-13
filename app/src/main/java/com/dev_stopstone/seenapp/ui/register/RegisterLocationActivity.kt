package com.dev_stopstone.seenapp.ui.register

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dev_stopstone.seenapp.R
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLocationBinding
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class RegisterLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityRegisterLocationBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private val marker = Marker()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync(this)

        binding.btnRegisterLocationButton.setOnClickListener {
            val key = intent.getStringExtra("key")!!
            val position = marker.position

            val bundle = Bundle()
            bundle.putDoubleArray("location", doubleArrayOf(position.latitude, position.longitude))
            bundle.putString("key", key)
            val modalBottomSheet = LocationBottomSheetFragment()
            modalBottomSheet.arguments = bundle
            modalBottomSheet.show(supportFragmentManager, LocationBottomSheetFragment.TAG)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 없는 경우, 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            val locationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            locationProviderClient.lastLocation.addOnSuccessListener { location ->
                val currentLatLng = LatLng(location.latitude, location.longitude)
                naverMap.moveCamera(CameraUpdate.scrollTo(currentLatLng))
                marker.position = currentLatLng
                marker.map = naverMap
            }
        }

        naverMap.addOnCameraChangeListener { _, _ ->
            val cameraLatLng = naverMap.cameraPosition.target
            marker.position = cameraLatLng
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}