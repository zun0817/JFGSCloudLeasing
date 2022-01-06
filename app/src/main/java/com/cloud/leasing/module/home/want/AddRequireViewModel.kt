package com.cloud.leasing.module.home.want

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
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddRequireViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val addRequireLiveData = MutableLiveData<Result<String>>()

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val provinceLiveData = MutableLiveData<Result<MutableList<ProvinceBean>>>()

    @PageName
    override fun getPageName() = PageName.ADDREQUIRE

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

    fun requestOfAddRequire(
        deviceBrand: String,
        deviceType: String,
        province: String,
        projectLocation: String,
        projectLength: String,
        geologicalInfo: String,
        demandNum: String,
        cutterDiam: String,
        turningRadius: String,
        outerDiameter: String,
        openingRate: String,
        usageTime: String,
        cutterType: String,
        drivingTorque: String,
        propulsiveForce: String,
        limitedRange: String
    ) {
        viewModelScope.launch {
            val params = getAddRequireParam(
                deviceBrand,
                deviceType,
                province,
                projectLocation,
                projectLength,
                geologicalInfo,
                demandNum,
                cutterDiam,
                turningRadius,
                outerDiameter,
                openingRate,
                usageTime,
                cutterType,
                drivingTorque,
                propulsiveForce,
                limitedRange
            )
            val result = NetworkApi.requestOfAddRequire(params)
            addRequireLiveData.value = result
        }
    }

    private fun getAddRequireParam(
        deviceBrand: String,
        deviceType: String,
        province: String,
        projectLocation: String,
        projectLength: String,
        geologicalInfo: String,
        demandNum: String,
        cutterDiam: String,
        turningRadius: String,
        outerDiameter: String,
        openingRate: String,
        usageTime: String,
        cutterType: String,
        drivingTorque: String,
        propulsiveForce: String,
        limitedRange: String
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["userId"] = userId
        map["deviceBrand"] = deviceBrand
        map["deviceType"] = deviceType
        map["province"] = province
        map["projectLocation"] = projectLocation
        map["projectLength"] = projectLength
        map["geologicalInfo"] = geologicalInfo
        map["demandNum"] = demandNum
        map["cutterDiam"] = cutterDiam
        map["turningRadius"] = turningRadius
        map["outerDiameter"] = outerDiameter
        map["openingRate"] = openingRate
        map["usageTime"] = usageTime
        map["cutterType"] = cutterType
        map["drivingTorque"] = drivingTorque
        map["propulsiveForce"] = propulsiveForce
        map["limitedRange"] = limitedRange
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}