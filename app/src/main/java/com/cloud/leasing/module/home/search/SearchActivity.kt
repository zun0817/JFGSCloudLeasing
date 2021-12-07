package com.cloud.leasing.module.home.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivitySearchBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: SearchViewModel by viewModels()

    override fun getPageName() = PageName.SEARCH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.searchBackImg.setOnClickListener(this)
        viewBinding.searchFilterTv.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchFilterTv)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.service_back_img -> this.finish()
            R.id.search_filter_tv -> {

            }
        }
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