package com.yoesuv.infomalangbatu.menu.gallery.adapters

import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.ItemGalleryViewModel

class GalleryAdapter(private val activity: FragmentActivity, private val listData: MutableList<GalleryModel>): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding: ItemGalleryBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_gallery, parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindData(listData[holder.adapterPosition])
    }


    class GalleryViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(galleryModel: GalleryModel){
            val viewModel = ItemGalleryViewModel(galleryModel)
            binding.itemGallery = viewModel
        }
    }
}