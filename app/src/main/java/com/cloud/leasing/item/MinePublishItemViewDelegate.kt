package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
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
        holder.viewBinding.minepublishItemTypeTv.text = item.value.deviceType
        holder.viewBinding.minepublishItemCityTv.text = item.value.projectLocation
        holder.viewBinding.minepublishItemLengthTv.text = item.value.projectLength.toString()
        holder.viewBinding.minepublishItemProductTv.text = item.value.deviceBrand
        holder.viewBinding.minepublishItemNumTv.text = item.value.demandNum.toString()
        holder.viewBinding.minepublishItemGeologyTv.text = item.value.geologicalInfo
        holder.viewBinding.minepublishItemTimeTv.text = item.value.usageTime
        if (item.value.isVisible) {
            holder.viewBinding.minepublishSelectImg.visibility = View.VISIBLE
        } else {
            holder.viewBinding.minepublishSelectImg.visibility = View.GONE
        }
        if (item.value.isSelect) {
            holder.viewBinding.minepublishSelectImg.setImageResource(R.mipmap.icon_select_yes)
        } else {
            holder.viewBinding.minepublishSelectImg.setImageResource(R.mipmap.icon_select_no)
        }
        holder.viewBinding.minepublishSelectImg.setOnClickListener {
            performItemChildViewClick(it, item, holder, item.value)
        }
    }

    class ViewHolder(val viewBinding: LayoutMinepublishItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}