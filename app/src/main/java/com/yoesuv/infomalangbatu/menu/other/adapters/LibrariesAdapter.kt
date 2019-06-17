package com.yoesuv.infomalangbatu.menu.other.adapters

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemLibrariesBinding
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ItemLibrariesViewModel

class LibrariesAdapter(var context: Context, var listLibraries: MutableList<LibraryModel>): RecyclerView.Adapter<LibrariesAdapter.LibrariesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrariesViewHolder {
        val binding: ItemLibrariesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_libraries, parent, false)
        return LibrariesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listLibraries.size
    }

    override fun onBindViewHolder(holder: LibrariesViewHolder, position: Int) {
        holder.bindData(listLibraries[holder.adapterPosition])
    }

    class LibrariesViewHolder(val binding: ItemLibrariesBinding?) : RecyclerView.ViewHolder(binding?.root!!) {

        fun bindData(libraryModel: LibraryModel){
            binding?.library = ItemLibrariesViewModel(libraryModel)
        }

    }

}