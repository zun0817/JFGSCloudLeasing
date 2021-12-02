package com.cloud.leasing.module.home.publish

import android.os.Bundle
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityPublishBinding

class PublishActivity : BaseActivity<ActivityPublishBinding>(ActivityPublishBinding::inflate) {

    override fun getPageName() = PageName.PUBLISH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }

}