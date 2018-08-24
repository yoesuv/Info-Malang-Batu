package com.yoesuv.infomalangbatu.menu.other.adapters

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yoesuv.infomalangbatu.R
import com.yoesuv.infomalangbatu.databinding.ItemChangelogBinding
import com.yoesuv.infomalangbatu.menu.other.models.ChangeLogModel
import com.yoesuv.infomalangbatu.menu.other.viewmodels.ItemChangeLogViewModel

class ChangeLogAdapter(val activity: FragmentActivity): RecyclerView.Adapter<ChangeLogAdapter.ChangeLogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeLogViewHolder {
        val binding: ItemChangelogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_changelog, parent, false)
        return ChangeLogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ChangeLogViewHolder, position: Int) {
        holder.bindData(activity, null)
    }

    class ChangeLogViewHolder(val binding: ItemChangelogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(activity: FragmentActivity, changeLogModel: ChangeLogModel?){
            val viewModel = ViewModelProviders.of(activity).get(ItemChangeLogViewModel::class.java)
            binding.changelog = viewModel
        }

    }

}