package com.cloud.leasing.adapter

import com.bumptech.glide.Glide
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.R
import com.cloud.leasing.bean.BannerBean
import com.cloud.leasing.constant.Constant
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class SimpleAdapter : BaseBannerAdapter<BannerBean>() {

    override fun bindData(
        holder: BaseViewHolder<BannerBean>,
        data: BannerBean,
        position: Int,
        pageSize: Int
    ) {
        //holder.setImageResource(R.id.banner_image, data)
        Glide.with(JFGSApplication.instance).load(Constant.BASE_FILE_URL + data.noticeAppImage)
            .into(holder.findViewById(R.id.banner_image))
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.layout_banner_item
    }
}
