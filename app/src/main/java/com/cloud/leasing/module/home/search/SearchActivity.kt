package com.cloud.leasing.module.home.search

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.fastjson.JSON
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivitySearchBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.hideKeyboard
import com.cloud.leasing.util.toast
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

    private var searchDeviceFragment: SearchDeviceFragment? = null

    private var searchRequireFragment: SearchRequireFragment? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun getPageName() = PageName.SEARCH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        initFragment()
        viewModelObserve()
    }

    private fun viewModelObserve() {
        viewModel.apply {
            searchDeviceLiveData.observe(this@SearchActivity, { it ->
                viewBinding.searchLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    if (it.sbData.sbTotal == 0 && it.xqData.xqTotal == 0) {
                        viewBinding.searchTabLl.visibility = View.GONE
                    } else {
                        viewBinding.searchTabLl.visibility = View.VISIBLE
                        viewBinding.searchDeviceTv.text = "待租信息(" + it.sbData.sbTotal + ")"
                        val sbList = it.sbData.sbList
                        searchDeviceFragment?.apply {
                            refreshData(sbList)
                        }
                    }
                }
            })
            searchRequireLiveData.observe(this@SearchActivity, { it ->
                viewBinding.searchLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@SearchActivity)
                }.onSuccess {
                    if (it.sbData.sbTotal == 0 && it.xqData.xqTotal == 0) {
                        viewBinding.searchTabLl.visibility = View.GONE
                    } else {
                        viewBinding.searchTabLl.visibility = View.VISIBLE
                        viewBinding.searchRequireTv.text = "求租信息(" + it.xqData.xqTotal + ")"
                        val xqList = it.xqData.xqList
                        searchRequireFragment?.apply {
                            refreshData(xqList)
                        }
                    }
                }
            })
        }
    }

    private fun initView() {
        viewBinding.searchBackImg.setOnClickListener(this)
        viewBinding.searchFilterTv.setOnClickListener(this)
        viewBinding.searchDeviceLl.setOnClickListener(this)
        viewBinding.searchRequireLl.setOnClickListener(this)
        viewBinding.searchTv.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.searchFilterTv)
    }

    override fun onClick(v: View?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (v!!.id) {
            R.id.service_back_img -> this.finish()
            R.id.search_filter_tv -> {

            }
            R.id.search_device_ll -> {
                viewBinding.searchDeviceTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.searchDeviceView.visibility = View.VISIBLE
                viewBinding.searchRequireTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.searchRequireView.visibility = View.GONE
                checkNull()
                fragmentTransaction.hide(searchRequireFragment!!)
                fragmentTransaction.show(searchDeviceFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.search_require_ll -> {
                viewBinding.searchRequireTv.setTextColor(resources.getColor(R.color.color_0E64BC))
                viewBinding.searchRequireView.visibility = View.VISIBLE
                viewBinding.searchDeviceTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.searchDeviceView.visibility = View.GONE
                checkNull()
                fragmentTransaction.hide(searchDeviceFragment!!)
                fragmentTransaction.show(searchRequireFragment!!)
                fragmentTransaction.commitAllowingStateLoss()
            }
            R.id.search_tv -> {
                viewBinding.searchSearchEt.hideKeyboard()
                val keyWord = viewBinding.searchSearchEt.text.trim().toString()
                viewModel.requestOfQueryData(1, keyWord)
                viewModel.requestOfQueryData(2, keyWord)
                viewBinding.searchLoadingview.visibility = View.VISIBLE
            }
        }
    }

    private fun initFragment() {
        searchDeviceFragment = SearchDeviceFragment.newInstance()
        searchRequireFragment = SearchRequireFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.search_frame, searchDeviceFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction()
            .add(R.id.search_frame, searchRequireFragment!!)
            .commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().hide(searchRequireFragment!!)
            .commitAllowingStateLoss()
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

    private fun checkNull() {
        if (searchDeviceFragment == null) {
            searchDeviceFragment = SearchDeviceFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.search_frame, searchDeviceFragment!!)
                .commitAllowingStateLoss()
        }
        if (searchRequireFragment == null) {
            searchRequireFragment = SearchRequireFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.search_frame, searchRequireFragment!!)
                .commitAllowingStateLoss()
        }
    }
}