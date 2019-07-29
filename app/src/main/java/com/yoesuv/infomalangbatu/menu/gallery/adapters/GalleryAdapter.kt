package com.yoesuv.infomalangbatu.menu.gallery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.ItemGalleryViewModel

class GalleryAdapter: ListAdapter<GalleryModel, GalleryAdapter.GalleryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.item_gallery, parent,false)
        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindData(getItem(holder.adapterPosition))
    }

    companion object DiffCallback: DiffUtil.ItemCallback<GalleryModel>() {
        override fun areContentsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
            return oldItem.caption == newItem.caption
        }
    }

    class GalleryViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(galleryModel: GalleryModel){
            val viewModel = ItemGalleryViewModel(galleryModel)
            binding.itemGallery = viewModel
        }
    }
}