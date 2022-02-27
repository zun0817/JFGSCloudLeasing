package com.cloud.leasing.module.device.resume

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.cloud.leasing.R
import com.cloud.leasing.adapter.ResumeDetailFileAdapter
import com.cloud.leasing.adapter.ResumeDetailPictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.DeviceFlie
import com.cloud.leasing.bean.DeviceType
import com.cloud.leasing.bean.ResumeDetailBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityDeviceResumeDetailBinding
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.FileManager
import com.cloud.leasing.util.HttpDownload
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

class DeviceResumeDetailActivity :
    BaseActivity<ActivityDeviceResumeDetailBinding>(ActivityDeviceResumeDetailBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(
            activity: Activity,
            resumeId: Int,
            deviceResumeStatus: String,
            deviceNo: String
        ) {
            val intent = Intent()
            intent.setClass(activity, DeviceResumeDetailActivity::class.java)
            intent.putExtra("resumeId", resumeId)
            intent.putExtra("deviceNo", deviceNo)
            intent.putExtra("deviceResumeStatus", deviceResumeStatus)
            activity.startActivity(intent)
        }
    }

    private var deviceNo = ""

    private var fileName = ""

    private var deviceResumeStatus = "1"

    private val fileList = mutableListOf<DeviceFlie>()

    private val pictureList = mutableListOf<DeviceFlie>()

    private var deviceTypes = mutableListOf<DeviceType>()

    private lateinit var resumeDetailFileAdapter: ResumeDetailFileAdapter

    private lateinit var resumeDetailPictureAdapter: ResumeDetailPictureAdapter

    private val viewModel: DeviceResumeDetailViewModel by viewModels()

    override fun getPageName() = PageName.DEVICE_RESUME_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        val json = XKeyValue.getString(Constant.DEVICE_TYPE)
        deviceTypes = JSON.parseArray(json, DeviceType::class.java)
        val resumeId = intent.getIntExtra("resumeId", 0)
        deviceNo = intent.getStringExtra("deviceNo").toString()
        deviceResumeStatus = intent.getStringExtra("deviceResumeStatus").toString()
        viewBinding.deviceResumeDetailBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.deviceResumeDetailBackImg)
        when (deviceResumeStatus) {
            "1", "3" -> {
                viewBinding.layoutResumeDetailInfo.resumeDetailCl.visibility = View.VISIBLE
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseCl.visibility = View.GONE
            }
            "2" -> {
                viewBinding.layoutResumeDetailInfo.resumeDetailCl.visibility = View.GONE
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseCl.visibility = View.VISIBLE
            }
        }
        viewModel.requestOfDeviceResume(resumeId)
        viewBinding.resumeDetailLoadingview.visibility = View.VISIBLE

        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.isAutoMeasureEnabled = true
        viewBinding.layoutResumeDetailFile.resumeDetailPictureRv.layoutManager = gridLayoutManager

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.isAutoMeasureEnabled = true
        viewBinding.layoutResumeDetailFile.resumeDetailFileRv.layoutManager = linearLayoutManager
    }

    private fun viewModelObserve() {
        viewModel.apply {
            resumeDetailLiveData.observe(this@DeviceResumeDetailActivity, { it ->
                viewBinding.resumeDetailLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@DeviceResumeDetailActivity)
                }.onSuccess {
                    setData(it)
                }
            })
            downloadFileLiveData.observe(this@DeviceResumeDetailActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@DeviceResumeDetailActivity)
                }.onSuccess {
                    FileManager.saveFile(it, fileName)
                    "下载成功，请到文件管理查看".toast(this@DeviceResumeDetailActivity)
                }
            })
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.device_resume_detail_back_img -> this.finish()
        }
    }

    private fun setData(bean: ResumeDetailBean) {
        when (deviceResumeStatus) {
            "1", "3" -> {
                viewBinding.layoutResumeDetailInfo.resumeDetailNameTv.text = deviceNo
                viewBinding.layoutResumeDetailInfo.resumeDetailTypeTv.text =
                    getDeviceTypeName(bean.deviceType)
                viewBinding.layoutResumeDetailInfo.resumeDetailExcavateTv.text = bean.workingDiam
                viewBinding.layoutResumeDetailInfo.resumeDetailSerialTv.text =
                    bean.projectNumber ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailClassTv.text =
                    bean.projectCatelog ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailBossTv.text =
                    bean.projectManager ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailUsecityTv.text = bean.useCity ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailUseTv.text = bean.deviceUser ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailAddressTv.text =
                    bean.projectAddress ?: "无"
                viewBinding.layoutResumeDetailInfo.resumeDetailAmountTv.text =
                    bean.contractAmount.toString()
                if (bean.packageTime == null) {
                    viewBinding.layoutResumeDetailInfo.resumeDetailPackageTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailInfo.resumeDetailPackageTv.text =
                        bean.packageTime.split(" ")[0]
                }
                if (bean.exitTime == null) {
                    viewBinding.layoutResumeDetailInfo.resumeDetailExitTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailInfo.resumeDetailExitTv.text =
                        bean.exitTime.split(" ")[0]
                }
                viewBinding.layoutResumeDetailInfo.resumeDetailStatusTv.text =
                    when (bean.deviceResumeStatus) {
                        "1" -> {
                            "维修"
                        }
                        "2" -> {
                            "使用"
                        }
                        else -> {
                            "存放"
                        }
                    }
                bean.deviceFlieList.takeIf { it.isNotEmpty() }?.onEach {
                    when (it.fileType) {
                        "1" -> {
                            fileList.add(it)
                        }
                        else -> {
                            pictureList.add(it)
                        }
                    }
                }
                resumeDetailPictureAdapter = ResumeDetailPictureAdapter(this, pictureList)
                viewBinding.layoutResumeDetailFile.resumeDetailPictureRv.adapter =
                    resumeDetailPictureAdapter
                resumeDetailFileAdapter = ResumeDetailFileAdapter(this, fileList)
                viewBinding.layoutResumeDetailFile.resumeDetailFileRv.adapter =
                    resumeDetailFileAdapter
                resumeDetailFileAdapter.setOnItemClickListener(object :
                    MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                        fileName = fileList[p2].fileName
                        val path =
                            Constant.BASE_URL + Constant.PATH_FILE_DOWNLOAD + "?fileName=" + fileName + "&path=" + fileList[p2].filePath
                        HttpDownload.getInstance().downLoadFile(path, fileName, fileList[p2].filePath.split("\\.")[1])
//                        viewModel.requestOfDownloadFile(
//                            fileList[p2].fileName,
//                            fileList[p2].filePath
//                        )
                    }

                    override fun onItemLongClick(
                        p0: View?,
                        p1: RecyclerView.ViewHolder?,
                        p2: Int
                    ): Boolean {
                        return false
                    }
                })
            }
            "2" -> {
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseNameTv.text = deviceNo
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseTypeTv.text =
                    getDeviceTypeName(bean.deviceType)
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseExcavateTv.text =
                    bean.workingDiam
                if (bean.ringWidth == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseRingwidthTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseRingwidthTv.text =
                        bean.ringWidth.toString()
                }
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseIntervalTv.text =
                    bean.intervalMileage ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseTestedTv.text =
                    bean.settlementRange ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseSerialTv.text =
                    bean.projectNumber ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseClassTv.text =
                    bean.projectCatelog ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseBossTv.text =
                    bean.projectManager ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseUsecityTv.text =
                    bean.useCity ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseUseTv.text =
                    bean.deviceUser ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseAddressTv.text =
                    bean.projectAddress ?: "无"
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseAmountTv.text =
                    bean.contractAmount.toString()
                if (bean.startTime == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseOriginTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseOriginTv.text =
                        bean.startTime.toString().split(" ")[0]
                }
                if (bean.intervalGeology == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseGeologyTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseGeologyTv.text =
                        bean.intervalGeology.toString()
                }
                if (bean.packageTime == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUsePackageTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUsePackageTv.text =
                        bean.packageTime.split(" ")[0]
                }
                if (bean.exitTime == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseExitTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseExitTv.text =
                        bean.exitTime.split(" ")[0]
                }
                if (bean.projectName == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseProjectTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseProjectTv.text =
                        bean.projectName.toString()
                }
                if (bean.inBlock == null) {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseSectionTv.text = "无"
                } else {
                    viewBinding.layoutResumeDetailUseInfo.resumeDetailUseSectionTv.text =
                        bean.inBlock.toString()
                }
                viewBinding.layoutResumeDetailUseInfo.resumeDetailUseStatusTv.text =
                    when (bean.deviceResumeStatus) {
                        "1" -> {
                            "维修"
                        }
                        "2" -> {
                            "使用"
                        }
                        else -> {
                            "存放"
                        }
                    }
                bean.deviceFlieList.takeIf { it.isNotEmpty() }?.onEach {
                    when (it.fileType) {
                        "1" -> {
                            fileList.add(it)
                        }
                        else -> {
                            pictureList.add(it)
                        }
                    }
                }
                resumeDetailPictureAdapter = ResumeDetailPictureAdapter(this, pictureList)
                viewBinding.layoutResumeDetailFile.resumeDetailPictureRv.adapter =
                    resumeDetailPictureAdapter
                resumeDetailFileAdapter = ResumeDetailFileAdapter(this, fileList)
                viewBinding.layoutResumeDetailFile.resumeDetailFileRv.adapter =
                    resumeDetailFileAdapter
                resumeDetailFileAdapter.setOnItemClickListener(object :
                    MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(p0: View?, p1: RecyclerView.ViewHolder?, p2: Int) {
                        fileName = fileList[p2].fileName
                        val path =
                            Constant.BASE_URL + Constant.PATH_FILE_DOWNLOAD + "?fileName=" + fileName + "&path=" + fileList[p2].filePath
                        HttpDownload.getInstance().downLoadFile(path, fileName, fileList[p2].filePath.split("\\.")[1])
//                        viewModel.requestOfDownloadFile(
//                            fileList[p2].fileName,
//                            fileList[p2].filePath
//                        )
                    }

                    override fun onItemLongClick(
                        p0: View?,
                        p1: RecyclerView.ViewHolder?,
                        p2: Int
                    ): Boolean {
                        return false
                    }
                })
            }
        }
    }

    private fun getDeviceTypeName(type: String): String {
        deviceTypes.forEach {
            if (type == it.value) {
                return it.name
            }
        }
        return "暂无"
    }

}