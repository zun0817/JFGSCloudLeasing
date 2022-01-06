package com.cloud.leasing.module.home.have

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddDeviceViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val addDeviceLiveData = MutableLiveData<Result<String>>()

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val provinceLiveData = MutableLiveData<Result<MutableList<ProvinceBean>>>()

    @PageName
    override fun getPageName() = PageName.ADDDEVICE

    fun requestOfDeviceType() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceType()
            deviceTypeLiveData.value = result
        }
    }

    fun requestOfDeviceSite() {
        viewModelScope.launch {
            val result = NetworkApi.requestOfDeviceSite()
            provinceLiveData.value = result
        }
    }

    fun requestOfUploadFile(file: File, fileType: String) {
        viewModelScope.launch {
            val mfile = getUploadFile(file)
            val param = getUploadParam(fileType)
            val result = NetworkApi.requestOfUploadFile(param, mfile)
            fileLiveData.value = result
        }
    }

    private fun getUploadFile(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("mf", file.name, requestFile)
    }

    private fun getUploadParam(fileType: String): RequestBody {
        return fileType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    fun requestOfAddDevice(
        deviceNo: String,
        deviceBrand: String,
        deviceType: String,
        deviceArea: String,
        cutterDiam: String,
        beamNum: String,
        propulsiveForce: String,
        hingeForm: String,
        cutterType: String,
        propertyOwner: String,
        drivingTorque: String,
        drivingPower: String,
        assetOwnership: String,
        applicableStratum: String,
        outerDiameter: String,
        deviceModel: String,
        drivingForm: String,
        openingRate: String,
        turningRadius: String,
        mileageUsed: String,
        drivingPosition: String,
        leaseTime: String,
        deviceRentStatus: String,
        deviceResume: String,
        deviceStatus: String,
        workingDiam: String,
        remarks: String
    ) {
        viewModelScope.launch {
            val params = getAddDeviceParam(
                deviceNo,
                deviceBrand,
                deviceType,
                deviceArea,
                cutterDiam,
                beamNum,
                propulsiveForce,
                hingeForm,
                cutterType,
                propertyOwner,
                drivingTorque,
                drivingPower,
                assetOwnership,
                applicableStratum,
                outerDiameter,
                deviceModel,
                drivingForm,
                openingRate,
                turningRadius,
                mileageUsed,
                drivingPosition,
                leaseTime,
                deviceRentStatus,
                deviceResume,
                deviceStatus,
                workingDiam,
                remarks
            )
            val result = NetworkApi.requestOfAddDevice(params)
            addDeviceLiveData.value = result
        }
    }

    private fun getAddDeviceParam(
        deviceNo: String,
        deviceBrand: String,
        deviceType: String,
        deviceArea: String,
        cutterDiam: String,
        beamNum: String,
        propulsiveForce: String,
        hingeForm: String,
        cutterType: String,
        propertyOwner: String,
        drivingTorque: String,
        drivingPower: String,
        assetOwnership: String,
        applicableStratum: String,
        outerDiameter: String,
        deviceModel: String,
        drivingForm: String,
        openingRate: String,
        turningRadius: String,
        mileageUsed: String,
        drivingPosition: String,
        leaseTime: String,
        deviceRentStatus: String,
        deviceResume: String,
        deviceStatus: String,
        workingDiam: String,
        remarks: String
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["userId"] = userId
        map["deviceNo"] = deviceNo
        map["deviceBrand"] = deviceBrand
        map["deviceType"] = deviceType
        map["deviceArea"] = deviceArea
        map["cutterDiam"] = cutterDiam
        map["beamNum"] = beamNum
        map["propulsiveForce"] = propulsiveForce
        map["hingeForm"] = hingeForm
        map["cutterType"] = cutterType
        map["propertyOwner"] = propertyOwner
        map["drivingTorque"] = drivingTorque
        map["drivingPower"] = drivingPower
        map["assetOwnership"] = assetOwnership
        map["applicableStratum"] = applicableStratum
        map["outerDiameter"] = outerDiameter
        map["deviceModel"] = deviceModel
        map["drivingForm"] = drivingForm
        map["openingRate"] = openingRate
        map["turningRadius"] = turningRadius
        map["mileageUsed"] = mileageUsed
        map["drivingPosition"] = drivingPosition
        map["leaseTime"] = leaseTime
        map["deviceRentStatus"] = deviceRentStatus
        map["deviceResume"] = deviceResume
        map["deviceStatus"] = deviceStatus
        map["workingDiam"] = workingDiam
        map["remarks"] = remarks
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}