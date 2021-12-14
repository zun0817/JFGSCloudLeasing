package com.cloud.leasing.module.home.have

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.base.list.XRecyclerView
import com.cloud.leasing.base.list.base.BaseViewData
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityHaveBinding
import com.cloud.leasing.module.home.search.SearchActivity
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class HaveActivity : BaseActivity<ActivityHaveBinding>(ActivityHaveBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, HaveActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: HaveViewModel by viewModels()

    override fun getPageName() = PageName.HAVE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_0E64BC)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    private fun initView() {
        viewBinding.haveBackImg.setOnClickListener(this)
        viewBinding.haveSearchImg.setOnClickListener(this)
        viewBinding.haveAddDeviceTv.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.haveBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.haveSearchImg)

        viewBinding.haveRecyclerview.init(
            XRecyclerView.Config()
                .setViewModel(viewModel)
                .setOnItemClickListener(object : XRecyclerView.OnItemClickListener {
                    override fun onItemClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(
                            this@HaveActivity,
                            "条目点击: ${viewData.value}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
                .setOnItemChildViewClickListener(object :
                    XRecyclerView.OnItemChildViewClickListener {
                    override fun onItemChildViewClick(
                        parent: RecyclerView,
                        view: View,
                        viewData: BaseViewData<*>,
                        position: Int,
                        id: Long,
                        extra: Any?
                    ) {
                        if (extra is String) {
                            Toast.makeText(
                                this@HaveActivity,
                                "条目子View点击: $extra",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        )
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.have_back_img -> this.finish()
            R.id.have_search_img -> SearchActivity.startActivity(this)
            R.id.have_add_device_tv -> AddDeviceActivity.startActivity(this)
        }
    }

}