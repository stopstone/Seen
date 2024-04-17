package com.dev_stopstone.seenapp.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev_stopstone.seenapp.databinding.FragmentItemBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ItemBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentItemBottomSheetBinding? = null
    private val binding get() = _binding!!

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

        TODO("Bottom Sheet에서 주변 분실물만 가져와서 보여주기")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}