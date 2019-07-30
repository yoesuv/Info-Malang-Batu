package com.yoesuv.infomalangbatu.menu.listplace.adapters

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.ItemListPlaceViewModel

class ListPlaceAdapter(val onItemClick:(PlaceModel) -> Unit): ListAdapter<PlaceModel, ListPlaceAdapter.ListPlaceViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemListplaceBinding = DataBindingUtil.inflate(inflater, R.layout.item_listplace, parent, false)
        return ListPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListPlaceViewHolder, position: Int) {
        val fixPosition = holder.adapterPosition
        holder.setupData(getItem(fixPosition))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(fixPosition))
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<PlaceModel>() {
        override fun areContentsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: PlaceModel, newItem: PlaceModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

    class ListPlaceViewHolder(val binding: ItemListplaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setupData(placeModel: PlaceModel){
            val viewModel = ItemListPlaceViewModel(placeModel)
            binding.itemListPlace = viewModel
        }

    }

}