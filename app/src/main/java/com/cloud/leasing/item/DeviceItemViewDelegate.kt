package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.ItemTest2Binding
import com.cloud.leasing.databinding.LayoutDeviceItemBinding

class DeviceItemViewDelegate :
    BaseItemViewDelegate<DeviceItemViewData, DeviceItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutDeviceItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: DeviceItemViewData) {
        super.onBindViewHolder(holder, item)
        holder.viewBinding.deviceItemNameTv.text = item.value
    }

    class ViewHolder(val viewBinding: LayoutDeviceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}