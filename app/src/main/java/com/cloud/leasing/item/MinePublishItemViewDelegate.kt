package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutMinepublishItemBinding

class MinePublishItemViewDelegate :
    BaseItemViewDelegate<MinePublishItemViewData, MinePublishItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutMinepublishItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MinePublishItemViewData) {
        super.onBindViewHolder(holder, item)
        holder.viewBinding.minepublishItemTypeTv.text = item.value.name
        if (item.value.isSelect) {
            holder.viewBinding.minepublishSelectImg.visibility = View.VISIBLE
        } else {
            holder.viewBinding.minepublishSelectImg.visibility = View.GONE
        }
    }

    class ViewHolder(val viewBinding: LayoutMinepublishItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}