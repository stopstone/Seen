package com.dev_stopstone.seen

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_stopstone.seen.databinding.ItemLostBinding

class LostItemAdapter(private val items: List<LostItem>, private val listener: ItemClickListener) :
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
                val imageUri: Uri = lostItem.itemUrlImage.toUri()
                Glide.with(ivLostItemThumbnailImage)
                    .load(imageUri)
                    .centerCrop()
                    .into(ivLostItemThumbnailImage)
                tvLostItemTitle.text = lostItem.title
                tvLostItemLocation.text = lostItem.location
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