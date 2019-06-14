package com.yoesuv.infomalangbatu.menu.gallery.adapters

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemGalleryBinding
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.gallery.viewmodels.ItemGalleryViewModel
import java.lang.ref.WeakReference

class GalleryAdapter(private val activity: androidx.fragment.app.FragmentActivity, private val listData: MutableList<GalleryModel>): androidx.recyclerview.widget.RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding: ItemGalleryBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_gallery, parent, false)
        return GalleryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bindData(activity, listData[holder.adapterPosition])
    }

    class GalleryViewHolder(val binding: ItemGalleryBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bindData(activity: androidx.fragment.app.FragmentActivity, galleryModel: GalleryModel){
            val act = WeakReference<androidx.fragment.app.FragmentActivity>(activity)
            val viewModel = ItemGalleryViewModel(act, galleryModel)
            binding.itemGallery = viewModel
        }
    }
}