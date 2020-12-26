package com.yoesuv.infomalangbatu.menu.other.adapters.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yoesuv.infomalangbatu.databinding.ItemChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ItemChangeLogViewModel

class ChangeLogViewHolder(val binding: ItemChangelogBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(changeLogModel: ChangeLogModel) {
        binding.changelog = ItemChangeLogViewModel(changeLogModel)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ChangeLogViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemChangelogBinding.inflate(inflater, parent, false)
            return ChangeLogViewHolder(binding)
        }
    }

}