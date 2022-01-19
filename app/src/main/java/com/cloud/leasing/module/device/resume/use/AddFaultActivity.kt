package com.cloud.leasing.module.device.resume.use

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddFaultFileAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.ProductDailyFaultBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddFaultBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.sky.filepicker.upload.Constants
import com.sky.filepicker.upload.LocalUpdateActivity
import java.io.File

class AddFaultActivity : BaseActivity<ActivityAddFaultBinding>(ActivityAddFaultBinding::inflate),
    View.OnClickListener {

    companion object {
        val REQUEST_CODE = 1001
        fun startActivityForResult(activity: Activity, resumeId: Int) {
            val intent = Intent()
            intent.putExtra("resumeId", resumeId)
            intent.setClass(activity, AddFaultActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private var fileUrl = ""

    private var resumeId = 0

    private var deleteFileIndex = 0

    private var fileList = mutableListOf<CompanyFileBean>()

    private var mPathList = mutableListOf<String>()

    private lateinit var addFaultFileAdapter: AddFaultFileAdapter

    private val viewModel: AddFaultViewModel by viewModels()

    override fun getPageName() = PageName.ADD_FAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_fault_back_img -> this.finish()
            R.id.add_fault_save_btn -> saveData()
            R.id.add_fault_file_fl -> {
                val intent = Intent(this, LocalUpdateActivity::class.java)
                intent.putExtra("maxNum", 1)
                startActivityForResult(intent, Constants.UPLOAD_FILE_REQUEST)
            }
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            fileLiveData.observe(this@AddFaultActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddFaultActivity)
                }.onSuccess {
                    "上传成功".toast(this@AddFaultActivity)
                    fileList.add(it)
                }
            })
            deleteFileLiveData.observe(this@AddFaultActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@AddFaultActivity)
                }.onSuccess {
                    "删除文件成功".toast(this@AddFaultActivity)
                    fileList.removeAt(deleteFileIndex)
                }
            })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.UPLOAD_FILE_REQUEST && resultCode == Constants.UPLOAD_FILE_RESULT) {
            data?.let { it ->
                val list: MutableList<String> =
                    it.getStringArrayListExtra("pathList") as MutableList<String>
                mPathList.addAll(list)
                addFaultFileAdapter.notifyDataSetChanged()
                list.forEach {
                    Log.d("地址：", it)
                    val file = File(it)
                    viewModel.requestOfUploadFile(file, "90")
                }
            }
        }
    }

    private fun saveData() {
        val downTime = viewBinding.layoutAddFaultInfo.addFaultDowntimeEt.text.trim().toString()
        val processTime =
            viewBinding.layoutAddFaultInfo.addFaultProcesstimeEt.text.trim().toString()
        val ringNumber = viewBinding.layoutAddFaultInfo.addFaultRingEt.text.trim().toString()
        val faultBody = viewBinding.layoutAddFaultInfo.addFaultSiteEt.text.trim().toString()
        val faultDesc = viewBinding.layoutAddFaultInfo.addFaultDescEt.text.trim().toString()

        val causeAnalysis = viewBinding.layoutAddFaultInfo.addFaultCauseEt.text.trim().toString()
        val repRecord = viewBinding.layoutAddFaultInfo.addFaultRecordEt.text.trim().toString()
        val repModel = viewBinding.layoutAddFaultInfo.addFaultModelEt.text.trim().toString()

        if (downTime.isBlank()) {
            "请输入异常停机时间".toast(this)
            return
        }
        if (processTime.isBlank()) {
            "请输入处理时长".toast(this)
            return
        }
        if (ringNumber.isBlank()) {
            "请输入环号".toast(this)
            return
        }
        if (faultBody.isBlank()) {
            "请输入故障部位".toast(this)
            return
        }
        if (faultDesc.isBlank()) {
            "请输入故障描述".toast(this)
            return
        }
        fileList.forEach {
            fileUrl = fileUrl + "," + it.filePath
        }
        val productDailyFaultBean = ProductDailyFaultBean(
            ringNumber,
            faultDesc,
            faultBody,
            causeAnalysis,
            repRecord,
            repModel,
            fileUrl,
            downTime,
            processTime,
            resumeId
        )
        val intent = Intent()
        intent.putExtra("bean", productDailyFaultBean)
        setResult(RESULT_OK, intent)
        this.finish()
    }

    private fun initView() {
        resumeId = intent.getIntExtra("resumeId", 0)
        viewBinding.addFaultBackImg.setOnClickListener(this)
        viewBinding.addFaultSaveBtn.setOnClickListener(this)
        viewBinding.layoutAddFaultUpload.addFaultFileFl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addFaultBackImg)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isAutoMeasureEnabled = true
        viewBinding.layoutAddFaultUpload.addFaultFileRv.layoutManager = linearLayoutManager
        addFaultFileAdapter = AddFaultFileAdapter(this, mPathList)
        viewBinding.layoutAddFaultUpload.addFaultFileRv.adapter = addFaultFileAdapter

        addFaultFileAdapter.setOnDeleteFileListener {
            deleteFileIndex = it
            viewModel.requestOfDeleteFile(fileList[it].filePath)
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