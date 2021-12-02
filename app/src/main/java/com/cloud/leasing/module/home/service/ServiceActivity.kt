package com.cloud.leasing.module.home.service

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
import com.cloud.leasing.databinding.ActivityServiceBinding
import com.gyf.immersionbar.ktx.immersionBar

class ServiceActivity : BaseActivity<ActivityServiceBinding>(ActivityServiceBinding::inflate) {

    private val viewModel: ServiceViewModel by viewModels()

    override fun getPageName() = PageName.SERVICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.serviceRecyclerview.init(
            XRecyclerView.Config()
                .setPullRefreshEnable(false)
                .setPullUploadMoreEnable(false)
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
                            this@ServiceActivity,
                            "条目点击: ${viewData.value}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                })
        )
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