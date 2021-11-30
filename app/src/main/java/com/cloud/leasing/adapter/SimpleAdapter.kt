package com.cloud.leasing.adapter

import com.cloud.leasing.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class SimpleAdapter : BaseBannerAdapter<Int>() {

    override fun bindData(holder: BaseViewHolder<Int>, data: Int, position: Int, pageSize: Int) {
        holder.setImageResource(R.id.banner_image, data)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.layout_banner_item
    }
}
