package com.yoesuv.infomalangbatu.utils

import androidx.recyclerview.widget.DiffUtil
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel

object AdapterCallbacks {

    val listPlaceCallback = object : DiffUtil.ItemCallback<PlaceModel>() {
        override fun areContentsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

    val galleryCallback = object : DiffUtil.ItemCallback<GalleryModel>() {
        override fun areContentsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean {
            return oldItem.caption == newItem.caption
        }
    }

}