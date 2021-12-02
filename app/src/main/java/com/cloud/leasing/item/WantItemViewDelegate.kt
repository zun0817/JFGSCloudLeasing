package com.cloud.leasing.item

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.list.base.BaseItemViewDelegate
import com.cloud.leasing.databinding.LayoutWantItemBinding

class WantItemViewDelegate :
    BaseItemViewDelegate<WantItemViewData, WantItemViewDelegate.ViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        context: Context,
        parent: ViewGroup
    ): ViewHolder {
        return ViewHolder(LayoutWantItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: WantItemViewData) {
        super.onBindViewHolder(holder, item)
        when (item.value) {
            1 -> {
                holder.viewBinding.wantItemLabelTv.text = "已匹配"
                holder.viewBinding.wantItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_0e64bc)
            }
            2 -> {
                holder.viewBinding.wantItemLabelTv.text = "未匹配"
                holder.viewBinding.wantItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_ffaa33)
            }
            3 -> {
                holder.viewBinding.wantItemLabelTv.text = "未通过"
                holder.viewBinding.wantItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_bfbfbf)
            }
            4 -> {
                holder.viewBinding.wantItemLabelTv.text = "待审核"
                holder.viewBinding.wantItemLabelTv.setBackgroundResource(R.drawable.shape_want_label_13ad6b)
            }
        }
    }

    class ViewHolder(val viewBinding: LayoutWantItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}