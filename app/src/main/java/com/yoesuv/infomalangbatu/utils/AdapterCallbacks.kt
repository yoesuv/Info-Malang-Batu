package com.yoesuv.infomalangbatu.utils

import androidx.recyclerview.widget.DiffUtil
import com.yoesuv.infomalangbatu.menu.gallery.models.GalleryModel
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel

object AdapterCallbacks {
    val listPlaceCallback =
        object : DiffUtil.ItemCallback<PlaceModel>() {
            override fun areContentsTheSame(
                oldItem: PlaceModel,
                newItem: PlaceModel,
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: PlaceModel,
                newItem: PlaceModel,
            ): Boolean = oldItem.name == newItem.name
        }

    val galleryCallback =
        object : DiffUtil.ItemCallback<GalleryModel>() {
            override fun areContentsTheSame(
                oldItem: GalleryModel,
                newItem: GalleryModel,
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: GalleryModel,
                newItem: GalleryModel,
            ): Boolean = oldItem.caption == newItem.caption
        }

    val libraryCallback =
        object : DiffUtil.ItemCallback<LibraryModel>() {
            override fun areContentsTheSame(
                oldItem: LibraryModel,
                newItem: LibraryModel,
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: LibraryModel,
                newItem: LibraryModel,
            ): Boolean = oldItem.title == newItem.title
        }

    val changeLogCallback =
        object : DiffUtil.ItemCallback<ChangeLogModel>() {
            override fun areItemsTheSame(
                oldItem: ChangeLogModel,
                newItem: ChangeLogModel,
            ): Boolean = oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: ChangeLogModel,
                newItem: ChangeLogModel,
            ): Boolean = oldItem == newItem
        }
}
