package com.cloud.leasing.item

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.databinding.LayoutDeviceItemBinding
import com.cloud.leasing.util.ViewTouchUtil

class EquipmentItemViewDelegate :
    BaseItemViewDelegate<EquipmentItemViewData, EquipmentItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutDeviceItemBinding.inflate(inflater, parent, false))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, item: EquipmentItemViewData) {
        super.onBindViewHolder(holder, item)
        holder.viewBinding.deviceItemNameTv.text = item.value.deviceBrand
        holder.viewBinding.deviceItemTypeTv.text = item.value.deviceType
        holder.viewBinding.deviceItemDiameterTv.text = "开挖直径 " + item.value.cutterDiam + "mm"
        when (item.value.focusStatus) {
            0 -> {
                val drawableLeft: Drawable = JFGSApplication.instance.resources.getDrawable(
                    R.mipmap.icon_follow_no
                )
                holder.viewBinding.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                    drawableLeft,
                    null, null, null
                )
                holder.viewBinding.deviceItemFollowTv.text = "关注"
                holder.viewBinding.deviceItemFollowTv.compoundDrawablePadding = 10
            }
            1 -> {
                val drawableLeft: Drawable = JFGSApplication.instance.resources.getDrawable(
                    R.mipmap.icon_follow_yes
                )
                holder.viewBinding.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                    drawableLeft,
                    null, null, null
                )
                holder.viewBinding.deviceItemFollowTv.text = "已关注"
                holder.viewBinding.deviceItemFollowTv.compoundDrawablePadding = 10
            }
        }
        ViewTouchUtil.expandViewTouchDelegate(holder.viewBinding.deviceItemFollowTv)
        holder.viewBinding.deviceItemFollowTv.setOnClickListener {
            performItemChildViewClick(it, item, holder, item.value)
        }
        Glide.with(JFGSApplication.instance)
            .load(Constant.BASE_FILE_URL + item.value.deviceMainFileUrl)
            .centerCrop()
            .placeholder(R.mipmap.icon_launcher_round)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.viewBinding.deviceItemImg)
    }

    class ViewHolder(val viewBinding: LayoutDeviceItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}