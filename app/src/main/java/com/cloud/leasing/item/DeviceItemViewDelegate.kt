package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.constant.Constant
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
        holder.viewBinding.deviceItemNameTv.text = item.value.drivingPosition + "·" + item.value.deviceNo
        holder.viewBinding.deviceItemTypeTv.text = item.value.deviceTypeName
        holder.viewBinding.deviceItemDiameterTv.text = "刀盘直径：" + item.value.cutterDiam + "mm"
        holder.viewBinding.deviceItemFollowTv.visibility = View.GONE
        holder.viewBinding.deviceItemResumeTv.visibility = View.VISIBLE
        Glide.with(JFGSApplication.instance)
            .load(Constant.BASE_FILE_URL + item.value.deviceImageUrl)
            .centerCrop()
            .placeholder(R.mipmap.icon_launcher_round)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.viewBinding.deviceItemImg)
        holder.viewBinding.deviceItemResumeTv.setOnClickListener {
            performItemChildViewClick(it, item, holder, item.value)
        }
    }

    class ViewHolder(val viewBinding: LayoutDeviceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}