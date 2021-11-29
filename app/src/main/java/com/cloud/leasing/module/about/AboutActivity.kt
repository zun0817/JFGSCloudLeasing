package com.cloud.leasing.module.about

import android.os.Bundle
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.EventName
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAboutBinding
import com.cloud.leasing.eventbus.XEventBus

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        viewBinding.tvAbout.setOnClickListener {
            XEventBus.post(EventName.TEST, "来自关于页面的消息")
        }
    }

    @PageName
    override fun getPageName() = PageName.ABOUT
}