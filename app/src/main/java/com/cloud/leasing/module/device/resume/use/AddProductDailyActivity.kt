package com.cloud.leasing.module.device.resume.use

import android.os.Bundle
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddProductDailyBinding

class AddProductDailyActivity :
    BaseActivity<ActivityAddProductDailyBinding>(ActivityAddProductDailyBinding::inflate) {


    override fun getPageName() = PageName.ADD_PRODUCT_DAILY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}