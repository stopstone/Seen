package com.dev_stopstone.seenapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.databinding.ItemDetailImageBinding
import com.google.firebase.storage.FirebaseStorage

class ItemImageAdapter(private val items: MutableList<String>) :
    RecyclerView.Adapter<ItemImageAdapter.ItemImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemImageViewHolder {
        return ItemImageViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ItemImageViewHolder(private val binding: ItemDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
            private val storage = FirebaseStorage.getInstance()
        fun bind(imageUri: String) {
            val storageRef = storage.getReferenceFromUrl(imageUri)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(itemView)
                    .load(uri)
                    .into(binding.image)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ItemImageViewHolder {
                val binding =
                    ItemDetailImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ItemImageViewHolder(binding)
            }
        }

    }
}