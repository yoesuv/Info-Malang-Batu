package com.yoesuv.infomalangbatu.menu.other.adapters

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ItemChangeLogViewModel

class ChangeLogAdapter(val activity: androidx.fragment.app.FragmentActivity, private var listChangeLog: MutableList<ChangeLogModel>): androidx.recyclerview.widget.RecyclerView.Adapter<ChangeLogAdapter.ChangeLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeLogViewHolder {
        val binding: ItemChangelogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_changelog, parent, false)
        return ChangeLogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listChangeLog.size
    }

    override fun onBindViewHolder(holder: ChangeLogViewHolder, position: Int) {
        val fixPosition = holder.adapterPosition
        holder.bindData(listChangeLog[fixPosition])
    }

    class ChangeLogViewHolder(val binding: ItemChangelogBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bindData(changeLogModel: ChangeLogModel?){
            binding.changelog = ItemChangeLogViewModel(changeLogModel)
        }

    }

}