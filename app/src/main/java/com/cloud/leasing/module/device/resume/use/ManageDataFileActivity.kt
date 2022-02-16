package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.ManageDataFileAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.ManageFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityManageDataFileBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class ManageDataFileActivity :
    BaseActivity<ActivityManageDataFileBinding>(ActivityManageDataFileBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(
            activity: Activity,
            resumeId: Int,
            parentId: Int,
            id: Int,
            manageName: String
        ) {
            val intent = Intent()
            intent.setClass(activity, ManageDataFileActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("resumeId", resumeId)
            intent.putExtra("parentId", parentId)
            intent.putExtra("manageName", manageName)
            activity.startActivity(intent)
        }
    }

    private var id = 0

    private var resumeId = 0

    private var parentId = 0

    private var manageName = ""

    private var list = mutableListOf<ManageFileBean>()

    private lateinit var manageDataFileAdapter: ManageDataFileAdapter

    private val viewModel: ManageDataFileViewModel by viewModels()

    override fun getPageName() = PageName.MANAGE_DATA_FILE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        id = intent.getIntExtra("id", 0)
        resumeId = intent.getIntExtra("resumeId", 0)
        parentId = intent.getIntExtra("parentId", 0)
        manageName = intent.getStringExtra("manageName").toString()
        viewBinding.manageDataFileTitleTv.text = manageName
        viewBinding.manageDataFileBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.manageDataFileBackImg)
        viewModel.requestOfManageFile(id)

        val linearLayoutManager = LinearLayoutManager(this)
        viewBinding.manageDataFileRv.layoutManager = linearLayoutManager
    }

    private fun viewModelObserve() {
        viewModel.apply {
            manageFileLiveData.observe(this@ManageDataFileActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@ManageDataFileActivity)
                }.onSuccess {
                    list = it
                    manageDataFileAdapter = ManageDataFileAdapter(this@ManageDataFileActivity, list)
                    viewBinding.manageDataFileRv.adapter = manageDataFileAdapter
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.manage_data_file_back_img -> this.finish()
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