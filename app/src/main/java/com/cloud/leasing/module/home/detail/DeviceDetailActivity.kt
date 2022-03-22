package com.cloud.leasing.module.home.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.DeviceDetailFileAdapter
import com.cloud.leasing.adapter.DeviceDetailPictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.RentDeviceFile
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityDeviceDetailBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class DeviceDetailActivity :
    BaseActivity<ActivityDeviceDetailBinding>(ActivityDeviceDetailBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener, NestedScrollView.OnScrollChangeListener {

    companion object {
        fun startActivity(activity: Activity, deviceId: Int) {
            val intent = Intent()
            intent.setClass(activity, DeviceDetailActivity::class.java)
            intent.putExtra("id", deviceId)
            activity.startActivity(intent)
        }
    }

    private var tabIndex = 0

    private var scrollviewFlag = false

    private val fileList = mutableListOf<RentDeviceFile>()

    private val pictureList = mutableListOf<RentDeviceFile>()

    private lateinit var detailFileAdapter: DeviceDetailFileAdapter

    private lateinit var detailPictureAdapter: DeviceDetailPictureAdapter

    private val viewModel: DeviceDetailViewModel by viewModels()

    override fun getPageName() = PageName.DEVICE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        val deviceId = intent.getIntExtra("id", 0)
        viewBinding.deviceDetailBackImg.setOnClickListener(this)
        viewBinding.layoutDevicePhone.phone1.setOnClickListener(this)
        viewBinding.layoutDevicePhone.phone2.setOnClickListener(this)
        viewBinding.deviceDetailScrollview.setOnScrollChangeListener(this)
        viewBinding.deviceDetailTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.deviceDetailBackImg)
        viewModel.requestOfDeviceDetail(deviceId)
        viewBinding.deviceDetailLoadingview.visibility = View.VISIBLE

        val gridLayoutManager = GridLayoutManager(this, 3)
        viewBinding.layoutDeviceDetailFile.deviceDetailPictureRv.layoutManager = gridLayoutManager

        val linearLayoutManager = LinearLayoutManager(this)
        viewBinding.layoutDeviceDetailFile.deviceDetailFileRv.layoutManager = linearLayoutManager
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceDetailLiveData.observe(this@DeviceDetailActivity, { it ->
                viewBinding.deviceDetailLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@DeviceDetailActivity)
                }.onSuccess { it ->
                    viewBinding.layoutDeviceDetailInfo.deviceDetailNameTv.text = it.deviceBrandName
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDateTv.text = "可租赁时间：" +
                            it.leaseTime
                    viewBinding.layoutDeviceDetailInfo.deviceDetailBrandTv.text = it.deviceBrandName
                    viewBinding.layoutDeviceDetailInfo.deviceDetailTypeTv.text = it.deviceTypeName
                    viewBinding.layoutDeviceDetailInfo.deviceDetailPlaceTv.text = it.deviceCity
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDiameterTv.text =
                        if (it.cutterDiam.isBlank()) "暂无" else it.cutterDiam + "mm"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailBeamTv.text =
                        if (it.beamNum.isBlank()) "暂无" else it.beamNum + "个"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailThrustTv.text =
                        if (it.propulsiveForce.isBlank()) "暂无" else it.propulsiveForce + "T"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailJiaojieTv.text =
                        it.hingeFormName ?: "暂无"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailTorqueTv.text =
                        if (it.drivingTorque.isBlank()) "暂无" else it.drivingTorque + "KN·m"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailPowerTv.text =
                        if (it.drivingPower.isBlank()) "暂无" else it.drivingPower + "KW"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDevicenoTv.text =
                        if (it.deviceNo.isBlank()) "暂无" else it.deviceNo
                    viewBinding.layoutDeviceDetailInfo.deviceDetailAssetsTv.text =
                        if (it.assetOwnership.isBlank()) "暂无" else it.assetOwnership
                    viewBinding.layoutDeviceDetailInfo.deviceDetailLayerTv.text =
                        if (it.applicableStratum.isBlank()) "暂无" else it.applicableStratum
                    viewBinding.layoutDeviceDetailInfo.deviceDetailOuterTv.text =
                        if (it.outerDiameter.isBlank()) "暂无" else it.outerDiameter + "mm"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDriveTv.text =
                        if (it.drivingForm.isBlank()) "暂无" else it.drivingForm
                    viewBinding.layoutDeviceDetailInfo.deviceDetailOpeningTv.text =
                        if (it.openingRate.isBlank()) "暂无" else it.openingRate + "%"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailMileageTv.text =
                        if (it.mileageUsed.isBlank()) "暂无" else it.mileageUsed + "m"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailCuttertypeTv.text =
                        it.cutterTypeName
                    viewBinding.layoutDeviceDetailInfo.deviceDetailMinPriceTv.text =
                        if (it.minPrice == null) "暂无" else (it.minPrice.div(10000)).toString() + "万"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailMaxPriceTv.text =
                        if (it.maxPrice == null) "暂无" else (it.maxPrice.div(10000)).toString() + "万"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailStatusTv.text =
                        when (it.deviceRentStatus) {
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
                    viewBinding.layoutDeviceDetailInfo.deviceDetailRadiusTv.text =
                        if (it.turningRadius.isBlank()) "暂无" else it.turningRadius + "m"
                    viewBinding.layoutDeviceDetailRemark.deviceDetailRemarkTv.text =
                        if (it.remarks.isBlank()) "暂无" else it.remarks
                    viewBinding.layoutDeviceDetailResume.deviceDetailResumeTv.text =
                        if (it.deviceResume.isBlank()) "暂无" else it.deviceResume
                    it.rentDeviceFileList.takeIf { it.isNotEmpty() }?.onEach {
                        when (it.fileType) {
                            "1" -> {
                                fileList.add(it)
                            }
                            else -> {
                                pictureList.add(it)
                            }
                        }
                    }
                    detailPictureAdapter =
                        DeviceDetailPictureAdapter(
                            this@DeviceDetailActivity,
                            pictureList
                        )
                    viewBinding.layoutDeviceDetailFile.deviceDetailPictureRv.adapter =
                        detailPictureAdapter
                    if (fileList.size > 0) {
                        viewBinding.layoutDeviceDetailFile.deviceDetailFileRv.visibility =
                            View.VISIBLE
                        detailFileAdapter =
                            DeviceDetailFileAdapter(
                                this@DeviceDetailActivity,
                                fileList
                            )
                        viewBinding.layoutDeviceDetailFile.deviceDetailFileRv.adapter =
                            detailFileAdapter
                    }
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
            R.id.device_detail_back_img -> this.finish()
            R.id.phone1 -> {
                callPhone("18583940011")
            }
            R.id.phone2 -> {
                callPhone("18538222939")
            }
        }
    }

    private fun callPhone(phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (!scrollviewFlag) {
            when (tab.position) {
                0 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailInfoLl.top
                    )
                }
                1 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailResumeLl.top
                    )
                }
                2 -> {
                    viewBinding.deviceDetailScrollview.scrollTo(
                        0,
                        viewBinding.deviceDetailAnnexLl.top
                    )
                }
//                3 -> {
//                    viewBinding.deviceDetailScrollview.scrollTo(
//                        0,
//                        viewBinding.deviceDetailUserLl.top
//                    )
//                }
            }
        }
        scrollviewFlag = false
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        scrollviewFlag = true
        tabIndex = viewBinding.deviceDetailTablayout.selectedTabPosition
        if (scrollY < viewBinding.deviceDetailResumeLl.top) {
            if (tabIndex != 0) {//增加判断，如果滑动的区域是tableIndex=0对应的区域，则不改变tablayout的状态
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        0
                    )
                )
            }
        } else if (scrollY >= viewBinding.deviceDetailResumeLl.top && scrollY < viewBinding.deviceDetailAnnexLl.top) {
            if (tabIndex != 1) {
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        1
                    )
                )
            }
        } else if (scrollY >= viewBinding.deviceDetailAnnexLl.top) {
            if (tabIndex != 2) {
                viewBinding.deviceDetailTablayout.selectTab(
                    viewBinding.deviceDetailTablayout.getTabAt(
                        2
                    )
                )
            }
        }
//        else if (scrollY >= viewBinding.deviceDetailUserLl.top) {
//            if (tabIndex != 3) {
//                viewBinding.deviceDetailTablayout.selectTab(
//                    viewBinding.deviceDetailTablayout.getTabAt(
//                        3
//                    )
//                )
//            }
//        }
        scrollviewFlag = false
    }

}