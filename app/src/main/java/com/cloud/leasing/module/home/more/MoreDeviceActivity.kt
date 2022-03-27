package com.cloud.leasing.module.home.more

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.adapter.MoreDeviceAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.Search
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.callback.OnItemViewClickListener
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityMoreDeviceBinding
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class MoreDeviceActivity :
    BaseActivity<ActivityMoreDeviceBinding>(ActivityMoreDeviceBinding::inflate),
    View.OnClickListener, AdapterView.OnItemClickListener, OnItemViewClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, MoreDeviceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var textView: TextView? = null

    private var clickSearch: Search? = null

    private var clickFocusStatus = "0"

    private val viewModel: MoreDeviceViewModel by viewModels()

    private lateinit var moreDeviceAdapter: MoreDeviceAdapter

    private lateinit var list: MutableList<Search>

    override fun getPageName() = PageName.MORE_DEVICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initSystemBar()
        viewModelObserve()
    }

    private fun initView() {
        userAuth = XKeyValue.getString(Constant.USER_AUTH, "0")
        viewBinding.moreDeviceBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.moreDeviceBackImg)
        list = mutableListOf()
        moreDeviceAdapter = MoreDeviceAdapter(this, list)
        viewBinding.moreDeviceListview.adapter = moreDeviceAdapter
        //viewModel.requestOfHomeDevices()
        viewModel.requestOfQueryData(1)
        viewBinding.moreDeviceLoadingview.visibility = View.VISIBLE
        viewBinding.moreDeviceListview.onItemClickListener = this
        moreDeviceAdapter.onItemViewClickListener = this
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
//            deviceLiveData.observe(
//                this@MoreDeviceActivity, { it ->
//                    viewBinding.moreDeviceLoadingview.visibility = View.GONE
//                    it.onFailure {
//                        it.toString().toast(this@MoreDeviceActivity)
//                    }.onSuccess {
//                        list = it
//                        moreDeviceAdapter.refreshData(it)
//                    }
//                })
            searchDeviceLiveData.observe(this@MoreDeviceActivity, { it ->
                viewBinding.moreDeviceLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@MoreDeviceActivity)
                }.onSuccess {
                    list = it.sbData.sbList
                    moreDeviceAdapter.refreshData(list)
                }
            })
            followLiveData.observe(this@MoreDeviceActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "关注成功".toast(this@MoreDeviceActivity)
                        setFocusStatus()
//                        val drawableLeft: Drawable =
//                            resources.getDrawable(
//                                R.mipmap.icon_follow_yes
//                            )
//                        textView?.setCompoundDrawablesWithIntrinsicBounds(
//                            drawableLeft,
//                            null, null, null
//                        )
//                        textView?.text = "已关注"
//                        textView?.compoundDrawablePadding = 10
//                        viewModel.requestOfQueryData(1)
                    } else {
                        it.toString().toast(this@MoreDeviceActivity)
                    }
                }.onSuccess {
                    setFocusStatus()
//                    "关注成功".toast(this@MoreDeviceActivity)
//                    val drawableLeft: Drawable =
//                        resources.getDrawable(
//                            R.mipmap.icon_follow_yes
//                        )
//                    textView?.setCompoundDrawablesWithIntrinsicBounds(
//                        drawableLeft,
//                        null, null, null
//                    )
//                    textView?.text = "已关注"
//                    textView?.compoundDrawablePadding = 10
//                    viewModel.requestOfQueryData(1)
                }
            })
            unfollowLiveData.observe(this@MoreDeviceActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "取消关注".toast(this@MoreDeviceActivity)
                        setFocusStatus()
//                        val drawableLeft: Drawable =
//                            resources.getDrawable(
//                                R.mipmap.icon_follow_no
//                            )
//                        textView?.setCompoundDrawablesWithIntrinsicBounds(
//                            drawableLeft,
//                            null, null, null
//                        )
//                        textView?.text = "关注"
//                        textView?.compoundDrawablePadding = 10
//                        viewModel.requestOfQueryData(1)
                    } else {
                        it.toString().toast(this@MoreDeviceActivity)
                    }
                }.onSuccess {
                    "取消关注".toast(this@MoreDeviceActivity)
                    setFocusStatus()
//                    val drawableLeft: Drawable =
//                        resources.getDrawable(
//                            R.mipmap.icon_follow_no
//                        )
//                    textView?.setCompoundDrawablesWithIntrinsicBounds(
//                        drawableLeft,
//                        null, null, null
//                    )
//                    textView?.text = "关注"
//                    textView?.compoundDrawablePadding = 10
//                    viewModel.requestOfQueryData(1)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.more_device_back_img -> this.finish()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (userAuth == "0" || userAuth == "2") {
            "企业认证未通过，暂无权限查看详情".toast(this)
            return
        }
        DeviceDetailActivity.startActivity(this, list[position].id)
    }

    override fun onChildren(position: Int, data: Any, view: View) {
        textView = view as TextView
        clickSearch = data as Search
        clickFocusStatus = clickSearch!!.focusStatus
        when (clickFocusStatus) {
            "0" -> viewModel.requestOfAddFollow(clickSearch!!.id, "1")
            "1" -> viewModel.requestOfUnfollow(clickSearch!!.id, "0")
        }
    }

    private fun setFocusStatus(){
        when(clickFocusStatus){
            "0" -> {
                list.forEach {
                    if (clickSearch?.id == it.id){
                        it.focusStatus = "1"
                    }
                }
                moreDeviceAdapter.refreshData(list)
            }
            "1" -> {
                list.forEach {
                    if (clickSearch?.id == it.id){
                        it.focusStatus = "0"
                    }
                }
                moreDeviceAdapter.refreshData(list)
            }
        }
    }
}