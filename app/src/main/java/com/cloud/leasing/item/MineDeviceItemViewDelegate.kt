package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutHaveItemBinding
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
        when (item.value.id) {
            1 -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "已匹配"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_0e64bc)
            }
            2 -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "未匹配"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_ffaa33)
            }
            3 -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "未通过"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_bfbfbf)
            }
            4 -> {
                holder.viewBinding.minedeviceItemLabelTv.text = "待审核"
                holder.viewBinding.minedeviceItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_13ad6b)
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
            performItemChildViewClick(it, item, holder, "选择")
        }
    }

    class ViewHolder(val viewBinding: LayoutMinedeviceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}