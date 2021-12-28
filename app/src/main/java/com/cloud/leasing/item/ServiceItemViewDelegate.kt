package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutHaveItemBinding
import com.cloud.leasing.databinding.LayoutServiceItemBinding

class ServiceItemViewDelegate :
    BaseItemViewDelegate<ServiceItemViewData, ServiceItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutServiceItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ServiceItemViewData) {
        super.onBindViewHolder(holder, item)
        holder.viewBinding.serviceItemNameTv.text = item.value.noticeTitle
        holder.viewBinding.serviceItemDescribeTv.text = item.value.noticeDesc
        when (item.value.id % 2) {
            0 -> holder.viewBinding.serviceItemImg.setImageResource(R.mipmap.icon_service)
            else -> holder.viewBinding.serviceItemImg.setImageResource(R.mipmap.icon_repair)
        }
    }

    class ViewHolder(val viewBinding: LayoutServiceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}