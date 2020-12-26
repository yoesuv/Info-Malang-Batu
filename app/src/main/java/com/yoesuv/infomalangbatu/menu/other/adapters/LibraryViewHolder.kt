package com.yoesuv.infomalangbatu.menu.other.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoesuv.infomalangbatu.databinding.ItemLibrariesBinding
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ItemLibrariesViewModel

class LibraryViewHolder(val binding: ItemLibrariesBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(libraryModel: LibraryModel) {
        binding.library = ItemLibrariesViewModel(libraryModel)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): LibraryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemLibrariesBinding.inflate(inflater, parent, false)
            return LibraryViewHolder(binding)
        }
    }

}