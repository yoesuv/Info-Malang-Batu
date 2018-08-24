package com.yoesuv.infomalangbatu.menu.listplace.adapters

import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemListplaceBinding
import com.yoesuv.infomalangbatu.menu.listplace.models.PlaceModel
import com.yoesuv.infomalangbatu.menu.listplace.viewmodels.ItemListPlaceViewModel
import java.lang.ref.WeakReference

class ListPlaceAdapter(private val activity: FragmentActivity, private val listData: MutableList<PlaceModel>): RecyclerView.Adapter<ListPlaceAdapter.ListPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaceViewHolder {
        val binding: ItemListplaceBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_listplace, parent, false)
        return ListPlaceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.listData.size
    }

    override fun onBindViewHolder(holder: ListPlaceViewHolder, position: Int) {
        holder.setupData(activity, listData[holder.adapterPosition])
    }

    class ListPlaceViewHolder(val binding: ItemListplaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setupData(activity: FragmentActivity, placeModel: PlaceModel){
            val weakContext = WeakReference(activity)
            val viewModel = ItemListPlaceViewModel(weakContext, placeModel)
            binding.itemListPlace = viewModel
        }

    }

}