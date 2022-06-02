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
import com.cloud.leasing.databinding.LayoutMinedeviceItemBinding

class MineDeviceItemViewDelegate :
    BaseItemViewDelegate<MineDeviceItemViewData, MineDeviceItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutMinedeviceItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: MineDeviceItemViewData) {
        super.onBindViewHolder(holder, item)
        when (item.value.matchStatus) {
            "1" -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "已匹配"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_0e64bc)
            }
            "2" -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "未匹配"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_ffaa33)
                when (item.value.deviceStatus) {
                    "0" -> {
                        holder.viewBinding.minedeviceItemLabelTv.text = "待审核"
                        holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_13ad6b)
                    }
                    "1" -> {
                        holder.viewBinding.minedeviceItemLabelTv.text = "未通过"
                        holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_bfbfbf)
                    }
                }
            }
        }
        if (item.value.isVisible) {
            holder.viewBinding.minedeviceSelectImg.visibility = View.VISIBLE
        } else {
            holder.viewBinding.minedeviceSelectImg.visibility = View.GONE
        }
        if (item.value.isSelect) {
            holder.viewBinding.minedeviceSelectImg.setImageResource(R.mipmap.icon_select_yes)
        } else {
            holder.viewBinding.minedeviceSelectImg.setImageResource(R.mipmap.icon_select_no)
        }
        holder.viewBinding.minedeviceSelectImg.setOnClickListener {
            performItemChildViewClick(it, item, holder, item.value)
        }
        Glide.with(JFGSApplication.instance)
            .load(Constant.BASE_FILE_URL + item.value.deviceMainFileUrl)
            .centerCrop()
            .placeholder(R.mipmap.icon_launcher_round)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.viewBinding.minedeviceItemImg)
        holder.viewBinding.minedeviceItemNameTv.text = item.value.deviceBrand
        holder.viewBinding.minedeviceItemTypeTv.text = item.value.deviceType
        holder.viewBinding.minedeviceItemDiameterTv.text = "开挖直径 " + item.value.cutterDiam + "mm"
    }

    class ViewHolder(val viewBinding: LayoutMinedeviceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}