package com.cloud.leasing.module.home.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityRequireDetailBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class RequireDetailActivity :
    BaseActivity<ActivityRequireDetailBinding>(ActivityRequireDetailBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener, NestedScrollView.OnScrollChangeListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, RequireDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var tabIndex = 0

    private var scrollviewFlag = false

    override fun getPageName() = PageName.REQUIRE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.requireDetailBackImg.setOnClickListener(this)
        viewBinding.requireDetailScrollview.setOnScrollChangeListener(this)
        viewBinding.requireDetailTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.requireDetailBackImg)
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
            R.id.require_detail_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (!scrollviewFlag) {
            when (tab.position) {
                0 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailInfoLl.top
                    )
                }
                1 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailAnnexLl.top
                    )
                }
                2 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailUserLl.top
                    )
                }
            }
        }
        scrollviewFlag = false
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        scrollviewFlag = true
        tabIndex = viewBinding.requireDetailTablayout.selectedTabPosition
        if (scrollY < viewBinding.requireDetailAnnexLl.top) {
            if (tabIndex != 0) {//增加判断，如果滑动的区域是tableIndex=0对应的区域，则不改变tablayout的状态
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        0
                    )
                )
            }
        } else if (scrollY >= viewBinding.requireDetailAnnexLl.top && scrollY < viewBinding.requireDetailUserLl.top) {
            if (tabIndex != 1) {
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        1
                    )
                )
            }
        } else if (scrollY >= viewBinding.requireDetailUserLl.top) {
            if (tabIndex != 2) {
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        2
                    )
                )
            }
        }
        scrollviewFlag = false
    }
}