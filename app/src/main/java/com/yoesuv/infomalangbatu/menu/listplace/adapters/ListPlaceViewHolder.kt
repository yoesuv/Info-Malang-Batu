package com.yoesuv.infomalangbatu.menu.listplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoesuv.infomalangbatu.databinding.ItemListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.ItemListPlaceViewModel

class ListPlaceViewHolder(val binding: ItemListplaceBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(placeModel: PlaceModel) {
        binding.itemListPlace = ItemListPlaceViewModel(placeModel)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ListPlaceViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemListplaceBinding.inflate(inflater, parent, false)
            return ListPlaceViewHolder(binding)
        }
    }

}