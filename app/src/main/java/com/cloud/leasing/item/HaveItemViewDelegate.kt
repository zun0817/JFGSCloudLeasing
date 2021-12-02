package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutHaveItemBinding

class HaveItemViewDelegate :
    BaseItemViewDelegate<HaveItemViewData, HaveItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutHaveItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: HaveItemViewData) {
        super.onBindViewHolder(holder, item)
        when (item.value) {
            1 -> {
                holder.viewBinding.haveItemLabelTv.text = "已匹配"
                holder.viewBinding.haveItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_0e64bc)
            }
            2 -> {
                holder.viewBinding.haveItemLabelTv.text = "未匹配"
                holder.viewBinding.haveItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_ffaa33)
            }
            3 -> {
                holder.viewBinding.haveItemLabelTv.text = "未通过"
                holder.viewBinding.haveItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_bfbfbf)
            }
            4 -> {
                holder.viewBinding.haveItemLabelTv.text = "待审核"
                holder.viewBinding.haveItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_13ad6b)
            }
        }
    }

    class ViewHolder(val viewBinding: LayoutHaveItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}