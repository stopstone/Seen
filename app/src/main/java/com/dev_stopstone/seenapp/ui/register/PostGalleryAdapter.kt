package com.dev_stopstone.seenapp.ui.register

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_stopstone.seenapp.databinding.ItemRegisterPhotoBinding

class PostGalleryAdapter(
    private val items: MutableList<String>,
) : RecyclerView.Adapter<PostGalleryAdapter.PostGalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostGalleryViewHolder {
        return PostGalleryViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PostGalleryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PostGalleryViewHolder(private val binding: ItemRegisterPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageView: String) {
            Glide.with(binding.root)
                .load(imageView)
                .into(binding.ivRegisterPhoto)
        }

        companion object {
            fun from(parent: ViewGroup): PostGalleryViewHolder {
                val binding = ItemRegisterPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostGalleryViewHolder(binding)
            }
        }

    }

}