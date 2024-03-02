package com.dev_stopstone.seen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev_stopstone.seen.databinding.FragmentHomeBinding

class HomeFragment: Fragment(), ItemClickListener {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHomeItemList.adapter = LostItemAdapter(Storage.getDummyData(), this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickLostItem(lostItem: LostItem) {
        val action = HomeFragmentDirections.actionHomeToLostDetail(lostItem)
            findNavController().navigate(action)
    }
}