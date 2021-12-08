package com.cloud.leasing.module.mine.about

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAboutBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }

    @PageName
    override fun getPageName() = PageName.ABOUT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.aboutBackImg.setOnClickListener(this)
        viewBinding.aboutLawCl.setOnClickListener(this)
        viewBinding.aboutUserAgreementCl.setOnClickListener(this)
        viewBinding.aboutUpdateVersionCl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.aboutBackImg)
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

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.about_back_img -> this.finish()
            R.id.about_law_cl -> {}
            R.id.about_user_agreement_cl -> {}
            R.id.about_update_version_cl -> {}
        }
    }

}