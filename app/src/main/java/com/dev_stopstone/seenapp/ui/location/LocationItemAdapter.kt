package com.dev_stopstone.seenapp.ui.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.ItemLostBinding

class LocationItemAdapter(private val items: MutableList<LostItem>) :
    RecyclerView.Adapter<LocationItemAdapter.LocationItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationItemViewHolder {
        return LocationItemViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: LocationItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class LocationItemViewHolder(private val binding: ItemLostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): LocationItemViewHolder {
                val binding = ItemLostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LocationItemViewHolder(binding)
            }
        }

    }
}