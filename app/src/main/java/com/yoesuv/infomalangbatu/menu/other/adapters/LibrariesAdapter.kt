package com.yoesuv.infomalangbatu.menu.other.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yoesuv.infomalangbatu.menu.other.models.LibraryModel
import com.yoesuv.infomalangbatu.utils.AdapterCallbacks

class LibrariesAdapter: ListAdapter<LibraryModel, LibraryViewHolder>(AdapterCallbacks.libraryCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        return LibraryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

}