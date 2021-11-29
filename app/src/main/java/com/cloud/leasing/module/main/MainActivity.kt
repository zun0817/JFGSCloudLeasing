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
import com.cloud.leasing.module.gold.GoldFragment
import com.cloud.leasing.module.home.HomeFragment
import com.cloud.leasing.module.mine.MineFragment
import com.cloud.leasing.widget.NavigationView
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

    // 当前选中的底栏ID
    @TabId
    private var currentTabId = TabId.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        //updateTitle()
        initTabs()
    }

    @PageName
    override fun getPageName() = PageName.MAIN

    /**
     * 禁用左滑返回
     */
    override fun swipeBackEnable() = false

    /**
     * 状态栏导航栏初始化
     */
    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_2178D1)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    /**
     * 初始化底栏
     */
    private fun initTabs() {
        val tabs = listOf(
            Tab(
                TabId.HOME,
                getString(R.string.page_home),
                R.drawable.selector_btn_home,
                R.color.selector_main_textcolor,
                HomeFragment::class
            ),
            //Tab(TabId.SMALL_VIDEO, getString(R.string.page_small_video), R.drawable.selector_btn_small_video, SmallVideoFragment::class),
            //Tab(TabId.ACGN, getString(R.string.page_acgn), R.drawable.selector_btn_acgn, AcgnFragment::class),
            Tab(
                TabId.GOLD,
                getString(R.string.page_device),
                R.drawable.selector_btn_gold,
                R.color.selector_main_textcolor,
                GoldFragment::class
            ),
            Tab(
                TabId.MINE,
                getString(R.string.page_mine),
                R.drawable.selector_btn_mine,
                R.color.selector_main_textcolor,
                MineFragment::class
            )
        )

        viewBinding.fragmentTabHost.run {
            // 调用setup()方法，设置FragmentManager，以及指定用于装载Fragment的布局容器
            setup(this@MainActivity, supportFragmentManager, viewBinding.fragmentContainer.id)
            tabs.forEach {
                val (id, title, icon, color, fragmentClz) = it
                val tabSpec = newTabSpec(id).apply {
                    setIndicator(TabIndicatorView(this@MainActivity).apply {
                        viewBinding.tabIcon.setImageResource(icon)
                        viewBinding.tabTitle.setTextColor(color)
                        viewBinding.tabTitle.text = title
                    })
                }
                addTab(tabSpec, fragmentClz.java, null)
            }

            setOnTabChangedListener { tabId ->
                currentTabId = tabId
                //updateTitle()
            }
        }
    }

    /**
     * 更新标题
     */
    private fun updateTitle() {
        val title = when (currentTabId) {
            TabId.HOME -> getString(R.string.page_home)
            TabId.SMALL_VIDEO -> getString(R.string.page_small_video)
            TabId.ACGN -> getString(R.string.page_acgn)
            TabId.GOLD -> getString(R.string.page_gold)
            TabId.MINE -> getString(R.string.page_mine)
            else -> ""
        }

        viewBinding.navigationBar.setParameter(
            NavigationView.ParameterBuilder()
                .setShowBack(false)
                .setShowTitle(true)
                .setTitle(title)
        )
    }

    /**
     * 设置当前选中的TAB
     */
    private fun setCurrentTab(@TabId tabID: String) {
        viewBinding.fragmentTabHost.setCurrentTabByTag(tabID)
    }
}