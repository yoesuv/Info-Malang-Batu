package com.yoesuv.infomalangbatu.menu.gallery.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.utils.AdapterCallbacks

class GalleryAdapter(val onClick:(GalleryModel) -> Unit): ListAdapter<GalleryModel, GalleryViewHolder>(AdapterCallbacks.galleryCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val galleryModel = getItem(holder.adapterPosition)
        holder.bind(galleryModel)
        holder.itemView.setOnClickListener {
            onClick(galleryModel)
        }
    }

}