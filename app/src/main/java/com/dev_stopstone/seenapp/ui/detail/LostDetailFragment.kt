package com.dev_stopstone.seenapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.R
import com.dev_stopstone.seenapp.databinding.FragmentLostDetailBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker

class LostDetailFragment : Fragment(), OnMapReadyCallback {
    private val binding by lazy { FragmentLostDetailBinding.inflate(layoutInflater) }
    private val args: LostDetailFragmentArgs by navArgs()
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        with(binding) {
            val lostItem = args.lostItem

            Glide.with(this@LostDetailFragment)

            tvLostItemDetailState.text = "찾는 중"
            tvLostItemDetailTitle.text = lostItem.title
            tvLostItemDetailPublishAt.text = lostItem.createAt
            tvLostItemDescription.text = lostItem.description
            tvLostItemDate.text = lostItem.lostDate
            tvLostItemRewardPrice.text = "${lostItem.rewardPrice}"
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isScrollGesturesEnabled = false
        naverMap.uiSettings.isZoomControlEnabled = false

        val position = args.lostItem.location
        val latLng = LatLng(position!!.latitude, position.longitude)
        val cameraUpdate = CameraUpdate.scrollTo(latLng)
        naverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = latLng
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO
        marker.map = naverMap
    }
}