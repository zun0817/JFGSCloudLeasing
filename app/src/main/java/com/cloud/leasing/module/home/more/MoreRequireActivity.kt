package com.cloud.leasing.module.home.more

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MoreDeviceAdapter
import com.cloud.leasing.adapter.MoreRequireAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.HomeRequireBean
import com.cloud.leasing.bean.Search
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityMoreRequireBinding
import com.cloud.leasing.module.home.detail.RequireDetailActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class MoreRequireActivity :
    BaseActivity<ActivityMoreRequireBinding>(ActivityMoreRequireBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, MoreRequireActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: MoreRequireViewModel by viewModels()

    private lateinit var moreRequireAdapter: MoreRequireAdapter

    private lateinit var list: MutableList<Search>

    override fun getPageName() = PageName.MORE_REQUIRE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSystemBar()
        viewModelObserve()
    }

    private fun initView() {
        userAuth = XKeyValue.getString(Constant.USER_AUTH, "0")
        viewBinding.moreRequireBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.moreRequireBackImg)
        list = mutableListOf()
        moreRequireAdapter = MoreRequireAdapter(this, list)
        viewBinding.moreRequireListview.adapter = moreRequireAdapter
        //viewModel.requestOfHomeRequires()
        viewModel.requestOfQueryData(2)
        viewBinding.moreRequireLoadingview.visibility = View.VISIBLE
        viewBinding.moreRequireListview.onItemClickListener = this
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

    private fun viewModelObserve() {
        viewModel.apply {
//            requiresLiveData.observe(
//                this@MoreRequireActivity, { it ->
//                    viewBinding.moreRequireLoadingview.visibility = View.GONE
//                    it.onFailure {
//                        it.toString().toast(this@MoreRequireActivity)
//                    }.onSuccess {
//                        list = it
//                        moreRequireAdapter.refreshData(it)
//                    }
//                })
            searchRequireLiveData.observe(this@MoreRequireActivity, { it ->
                viewBinding.moreRequireLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@MoreRequireActivity)
                }.onSuccess {
                    list = it.xqData.xqList
                    moreRequireAdapter.refreshData(list)
                    viewBinding.moreRequireTitleTv.text = "????????????(" + it.xqData.xqTotal + ")"
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.more_require_back_img -> this.finish()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (userAuth == "0" || userAuth == "2") {
            "????????????????????????????????????????????????".toast(this)
            return
        }
        RequireDetailActivity.startActivity(this, list[position].id)
    }
}