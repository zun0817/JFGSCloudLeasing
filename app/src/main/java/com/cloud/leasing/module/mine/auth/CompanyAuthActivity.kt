package com.cloud.leasing.module.mine.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityCompanyAuthBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class CompanyAuthActivity :
    BaseActivity<ActivityCompanyAuthBinding>(ActivityCompanyAuthBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, CompanyAuthActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.COMPANYAUTH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.companyAuthBackImg.setOnClickListener(this)
        viewBinding.companyAuthCommitBtn.setOnClickListener(this)
        viewBinding.layoutCompanyAuthFail.companyAuthFailBtn.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.companyAuthBackImg)
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
        when (v!!.id) {
            R.id.company_auth_back_img -> this.finish()
            R.id.company_auth_commit_btn -> {}
            R.id.company_auth_fail_btn -> {}
        }
    }

}