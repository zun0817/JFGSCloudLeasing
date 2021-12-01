package com.cloud.leasing.module.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.Tab
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.constant.TabId
import com.cloud.leasing.databinding.ActivityMainBinding
import com.cloud.leasing.module.device.DeviceFragment
import com.cloud.leasing.module.home.HomeFragment
import com.cloud.leasing.module.mine.MineFragment
import com.cloud.leasing.widget.TabIndicatorView
import com.gyf.immersionbar.ktx.immersionBar

/**
 * 首页
 */
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: MainViewModel by viewModels()

    @TabId
    private var currentTabId = TabId.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initTabs()
        setCurrentTab(currentTabId)
    }

    @PageName
    override fun getPageName() = PageName.MAIN

    override fun swipeBackEnable() = false

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_2178D1)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    private fun initTabs() {
        val tabs = listOf(
            Tab(
                TabId.HOME,
                getString(R.string.page_home),
                R.drawable.selector_btn_home,
                HomeFragment::class
            ),
            Tab(
                TabId.DEVICE,
                getString(R.string.page_device),
                R.drawable.selector_btn_gold,
                DeviceFragment::class
            ),
            Tab(
                TabId.MINE,
                getString(R.string.page_mine),
                R.drawable.selector_btn_mine,
                MineFragment::class
            )
        )

        viewBinding.fragmentTabHost.run {
            // 调用setup()方法，设置FragmentManager，以及指定用于装载Fragment的布局容器
            setup(this@MainActivity, supportFragmentManager, viewBinding.fragmentContainer.id)
            tabs.forEach {
                val (id, title, icon, fragmentClz) = it
                val tabSpec = newTabSpec(id).apply {
                    setIndicator(TabIndicatorView(this@MainActivity).apply {
                        viewBinding.tabIcon.setImageResource(icon)
                        viewBinding.tabTitle.text = title
                    })
                }
                addTab(tabSpec, fragmentClz.java, null)
            }

            setOnTabChangedListener { tabId ->
                currentTabId = tabId
            }
        }
    }

    private fun setCurrentTab(@TabId tabID: String) {
        viewBinding.fragmentTabHost.setCurrentTabByTag(tabID)
    }
}