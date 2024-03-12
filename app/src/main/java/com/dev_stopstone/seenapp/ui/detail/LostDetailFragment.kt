package com.dev_stopstone.seenapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.databinding.FragmentLostDetailBinding

class LostDetailFragment : Fragment() {
    private val binding by lazy { FragmentLostDetailBinding.inflate(layoutInflater) }
    private val args: LostDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val lostItem = args.lostItem
            Glide.with(this@LostDetailFragment)
                .load(lostItem.itemUrlImage)
                .into(ivLostItemDetailImage)
            tvLostItemDetailState.text = "찾는 중"
            tvLostItemDetailTitle.text = lostItem.title
            tvLostItemDetailPublishAt.text = lostItem.createAt
            tvLostItemDescription.text = lostItem.description
            tvLostItemDate.text = lostItem.lostDate
            tvLostItemRewardPrice.text = "${lostItem.rewardPrice}"
        }
    }
}