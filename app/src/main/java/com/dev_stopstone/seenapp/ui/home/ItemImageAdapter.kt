package com.dev_stopstone.seenapp.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev_stopstone.seenapp.databinding.ItemLostBinding

class ItemImageAdapter(private val items : MutableList<String>) :
    RecyclerView.Adapter<ItemImageAdapter.ItemImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemImageViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class ItemImageViewHolder(private val binding: ItemLostBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}