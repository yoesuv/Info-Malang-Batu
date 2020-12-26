package com.yoesuv.infomalangbatu.menu.other.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.yoesuv.infomalangbatu.menu.other.adapters.viewholders.ChangeLogViewHolder
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.utils.AdapterCallbacks

class ChangeLogAdapter: ListAdapter<ChangeLogModel, ChangeLogViewHolder>(AdapterCallbacks.changeLogCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeLogViewHolder {
        return ChangeLogViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChangeLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}