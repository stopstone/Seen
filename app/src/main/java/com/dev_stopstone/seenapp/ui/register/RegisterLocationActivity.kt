package com.dev_stopstone.seenapp.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev_stopstone.seenapp.databinding.ActivityRegisterLocationBinding
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker


class RegisterLocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityRegisterLocationBinding
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 네이버 맵 객체 초기화
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("lj8zi1w1sr")
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)



        binding.btnRegisterLocationButton.setOnClickListener {
            val cameraPosition = naverMap.cameraPosition
            val centerLatLng = cameraPosition.target
            val latitude = centerLatLng.latitude
            val longitude = centerLatLng.longitude

            val key = intent.getStringExtra("key")!!

            val bundle = Bundle()
            bundle.putDoubleArray("location", doubleArrayOf(latitude, longitude))
            bundle.putString("key", key)
            val modalBottomSheet = LocationBottomSheetFragment()
            modalBottomSheet.arguments = bundle
            modalBottomSheet.show(supportFragmentManager, LocationBottomSheetFragment.TAG)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        // 맵 가운데에 마커 추가
        val marker = Marker()
        marker.position = naverMap.cameraPosition.target
        marker.map = naverMap

        // 카메라 이동 시 마커 위치 업데이트
        naverMap.addOnCameraChangeListener { _, _ ->
            marker.position = naverMap.cameraPosition.target
        }

        // 맵 중심 이동
        val cameraPosition = CameraPosition(
            naverMap.cameraPosition.target, // 현재 위치를 중심으로 설정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}