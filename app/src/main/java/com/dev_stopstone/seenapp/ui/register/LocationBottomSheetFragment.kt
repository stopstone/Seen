package com.dev_stopstone.seenapp.ui.register

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_stopstone.seenapp.data.Location
import com.dev_stopstone.seenapp.databinding.FragmentLocationBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LocationBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentLocationBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database

        val key = arguments?.getString("key").toString()
        val locationPosition = arguments?.getDoubleArray("location")

        binding.btnSubmitLocation.setOnClickListener {
            val locationTitle = binding.etLocationTitle.text.toString()
            val location = Location(
                id = key,
                title = locationTitle,
                latitude = locationPosition!![0],
                longitude = locationPosition[1]
            )
            val resultIntent = Intent()
            resultIntent.putExtra("result", location)
            requireActivity().setResult(RESULT_OK, resultIntent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}