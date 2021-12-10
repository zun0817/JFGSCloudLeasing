package com.cloud.leasing.module.home.want

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddRequireBinding
import com.gyf.immersionbar.ktx.immersionBar

class AddRequireActivity :
    BaseActivity<ActivityAddRequireBinding>(ActivityAddRequireBinding::inflate) {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddRequireActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.ADDREQUIRE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {

    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_0e62B8)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }
}