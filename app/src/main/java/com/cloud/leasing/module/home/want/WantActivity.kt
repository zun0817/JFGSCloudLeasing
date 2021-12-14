package com.cloud.leasing.module.home.want

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
import com.cloud.leasing.databinding.ActivityWantBinding
import com.cloud.leasing.module.home.search.SearchActivity
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class WantActivity : BaseActivity<ActivityWantBinding>(ActivityWantBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, WantActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val viewModel: WantViewModel by viewModels()

    override fun getPageName() = PageName.WANT

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
        viewBinding.wantBackImg.setOnClickListener(this)
        viewBinding.wantAddDeviceTv.setOnClickListener(this)
        viewBinding.wantSearchImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.wantSearchImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.wantBackImg)
        viewBinding.wantRecyclerview.init(
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
                            this@WantActivity,
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
                                this@WantActivity,
                                "条目子View点击: $extra",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.want_back_img -> this.finish()
            R.id.want_add_device_tv -> AddRequireActivity.startActivity(this)
            R.id.want_search_img -> SearchActivity.startActivity(this)
        }
    }

}