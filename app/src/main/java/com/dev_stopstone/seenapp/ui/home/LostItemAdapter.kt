package com.dev_stopstone.seenapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.data.LostItem
import com.dev_stopstone.seenapp.databinding.ItemLostBinding

class LostItemAdapter(
    private val items: MutableList<LostItem>,
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<LostItemAdapter.LostItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostItemViewHolder {
        return LostItemViewHolder.from(parent, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LostItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class LostItemViewHolder(
        private val binding: ItemLostBinding,
        private val listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lostItem: LostItem) {
            itemView.setOnClickListener {
                listener.onClickLostItem(lostItem)
            }
            with(binding) {
                Glide.with(itemView)
                    .load(lostItem.imageUris.first())
                    .into(ivLostItemThumbnailImage)
                tvLostItemTitle.text = lostItem.title
                tvLostItemLocation.text = lostItem.location!!.title
                tvLostItemPublishedAt.text = lostItem.createAt
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: ItemClickListener): LostItemViewHolder {
                val binding = ItemLostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LostItemViewHolder(binding, listener)
            }
        }

    }
}