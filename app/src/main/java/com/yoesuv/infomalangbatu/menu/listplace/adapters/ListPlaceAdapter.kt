package com.yoesuv.infomalangbatu.menu.listplace.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.utils.AdapterCallbacks

class ListPlaceAdapter(val onItemClick:(PlaceModel) -> Unit): ListAdapter<PlaceModel, ListPlaceViewHolder>(AdapterCallbacks.listPlaceCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaceViewHolder {
        return ListPlaceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListPlaceViewHolder, position: Int) {
        val placeModel = getItem(holder.adapterPosition)
        holder.bind(placeModel)
        holder.itemView.setOnClickListener {
            onItemClick(placeModel)
        }
    }

}