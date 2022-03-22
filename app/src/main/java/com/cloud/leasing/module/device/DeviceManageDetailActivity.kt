package com.cloud.leasing.module.device

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.CutterType
import com.cloud.leasing.bean.DeviceBrand
import com.cloud.leasing.bean.DeviceType
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityDeviceManageDetailBinding
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class DeviceManageDetailActivity :
    BaseActivity<ActivityDeviceManageDetailBinding>(ActivityDeviceManageDetailBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, deviceId: Int) {
            val intent = Intent()
            intent.setClass(activity, DeviceManageDetailActivity::class.java)
            intent.putExtra("id", deviceId)
            activity.startActivity(intent)
        }
    }

    private var drivingPosition = ""

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private val viewModel: DeviceManageDetailViewModel by viewModels()

    override fun getPageName() = PageName.DEVICE_MANAGE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        val json1 = XKeyValue.getString(Constant.DEVICE_TYPE)
        typeList = JSON.parseArray(json1, DeviceType::class.java)
        val json2 = XKeyValue.getString(Constant.DEVICE_CUTTER)
        cutterList = JSON.parseArray(json2, CutterType::class.java)
        val json3 = XKeyValue.getString(Constant.DEVICE_BRAND)
        brandList = JSON.parseArray(json3, DeviceBrand::class.java)
        val deviceId = intent.getIntExtra("id", 0)
        viewBinding.manageDetailBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.manageDetailBackImg)
        viewModel.requestOfDeviceManageDetail(deviceId)
        viewBinding.manageDetailLoadingview.visibility = View.VISIBLE
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
            R.id.manage_detail_back_img -> this.finish()
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceDetailLiveData.observe(this@DeviceManageDetailActivity, { it ->
                viewBinding.manageDetailLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@DeviceManageDetailActivity)
                }.onSuccess { it ->
                    drivingPosition = it.drivingPosition
                    viewModel.requestOfCityList(it.deviceArea)
                    if (it.leaseTime == null) {
                        viewBinding.layoutDeviceDetailInfo.deviceDetailDateTv.text =
                            "可租赁时间 : 暂未填写"
                    } else {
                        viewBinding.layoutDeviceDetailInfo.deviceDetailDateTv.text =
                            "可租赁时间 ：" + it.leaseTime.split(" ")[0]
                    }
                    val deviceBrand = it.deviceBrand
                    brandList.forEach {
                        if (it.value == deviceBrand) {
                            viewBinding.layoutDeviceDetailInfo.deviceDetailBrandTv.text = it.name
                            viewBinding.layoutDeviceDetailInfo.deviceDetailNameTv.text = it.name
                        }
                    }

                    val deviceType = it.deviceType
                    typeList.forEach {
                        if (it.value == deviceType) {
                            viewBinding.layoutDeviceDetailInfo.deviceDetailTypeTv.text = it.name
                        }
                    }

                    val cutterType = it.cutterType
                    cutterList.forEach {
                        if (it.value == cutterType) {
                            viewBinding.layoutDeviceDetailInfo.deviceDetailCuttertypeTv.text =
                                it.name
                        }
                    }

                    //viewBinding.layoutDeviceDetailInfo.deviceDetailPlaceTv.text = it.deviceArea
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDiameterTv.text =
                        if (it.cutterDiam.isBlank()) "暂无" else it.cutterDiam + "mm"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailBeamTv.text = if (it.beamNum.isBlank()) "暂无" else it.beamNum + "个"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailThrustTv.text =
                        if (it.propulsiveForce.isBlank()) "暂无" else it.propulsiveForce + "T"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailJiaojieTv.text = if (it.hingeForm.isBlank()) "暂无" else it.hingeForm
                    viewBinding.layoutDeviceDetailInfo.deviceDetailTorqueTv.text =
                        if (it.drivingTorque.isBlank()) "暂无" else it.drivingTorque + "KN·m"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailPowerTv.text =
                        if (it.drivingPower.isBlank()) "暂无" else it.drivingPower + "KW"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailDevicenoTv.text = if (it.deviceNo.isBlank()) "暂无" else it.deviceNo
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
                    viewBinding.layoutDeviceDetailInfo.deviceDetailRadiusTv.text =
                        if (it.turningRadius.isBlank()) "暂无" else it.turningRadius + "m"

//                    viewBinding.layoutDeviceDetailInfo.deviceDetailMinPriceTv.text =
//                        if (it.minPrice == null) "暂无" else (it.minPrice.div(10000)).toString() + "万"
//                    viewBinding.layoutDeviceDetailInfo.deviceDetailMaxPriceTv.text =
//                        if (it.maxPrice == null) "暂无" else (it.maxPrice.div(10000)).toString() + "万"

                    viewBinding.layoutDeviceDetailInfo.title19.text = "管理模式"
                    viewBinding.layoutDeviceDetailInfo.deviceDetailStatusTv.text =
                        when (it.propertyOwner) {
                            "1" -> "自有"
                            "2" -> "融资"
                            else -> "转租"
                        }
                    Glide.with(this@DeviceManageDetailActivity)
                        .load(Constant.BASE_FILE_URL + it.deviceImageUrl)
                        .centerCrop()
                        .placeholder(R.mipmap.icon_launcher_round)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(viewBinding.detailPictureImg)
                }
            })
            cityLiveData.observe(this@DeviceManageDetailActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@DeviceManageDetailActivity)
                }.onSuccess { list ->
                    list.forEach {
                        if (it.areaCode == drivingPosition) {
                            viewBinding.layoutDeviceDetailInfo.deviceDetailPlaceTv.text =
                                it.areaFullName
                        }
                    }
                }
            })
        }
    }

}