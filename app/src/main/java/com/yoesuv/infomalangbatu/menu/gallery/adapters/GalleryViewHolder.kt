package com.yoesuv.infomalangbatu.menu.gallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoesuv.infomalangbatu.databinding.ItemGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.ItemGalleryViewModel

class GalleryViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(galleryModel: GalleryModel){
        binding.itemGallery =  ItemGalleryViewModel(galleryModel)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): GalleryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGalleryBinding.inflate(inflater, parent, false)
            return GalleryViewHolder(binding)
        }
    }

}