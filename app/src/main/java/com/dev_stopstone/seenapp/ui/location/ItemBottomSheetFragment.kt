package com.dev_stopstone.seenapp.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.FragmentItemBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ItemBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: FirebaseDatabase
    private val items = mutableListOf<LostItem>()
    private val adapter = LocationItemAdapter(items)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database

        val behavior = BottomSheetBehavior.from(view.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        // 상태 변화 리스너 추가
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        // 드래그 중일 때 동작
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // 완전히 펼쳐졌을 때 동작
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // 완전히 접혔을 때 동작
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // 숨겨졌을 때 동작
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이딩 중일 때 동작
            }
        })
    }
}