package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutRequireItemBinding

class RequireItemViewDelegate :
    BaseItemViewDelegate<RequireItemViewData, RequireItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutRequireItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: RequireItemViewData) {
        super.onBindViewHolder(holder, item)
        holder.viewBinding.requireItemTimeTv.text = item.value
    }

    class ViewHolder(val viewBinding: LayoutRequireItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}