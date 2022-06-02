package com.cloud.leasing.module.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.bean.SearchBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SearchViewModel : BaseViewModel() {

    val deviceTypeLiveData = MutableLiveData<Result<DeviceTypeBean>>()

    val provinceLiveData = MutableLiveData<Result<MutableList<ProvinceBean>>>()

    val searchDeviceLiveData = MutableLiveData<Result<SearchBean>>()

    val searchRequireLiveData = MutableLiveData<Result<SearchBean>>()

    @PageName
    override fun getPageName() = PageName.SEARCH

    fun requestOfQueryData(
        type: Int,
        key: String,
        value: String?
    ) {
        viewModelScope.launch {
            val param = getSearchParam(type, key, value)
            val result = NetworkApi.requestOfQueryData(param)
            when (type) {
                1 -> searchDeviceLiveData.value = result
                2 -> searchRequireLiveData.value = result
            }
        }
    }

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

    fun requestOfQueryDataOfDevice(
        deviceBrand: String?,
        deviceType: String?,
        cutterType: String?,
        deviceArea: String?,
        deviceStatus: String?,
        cutterDiamMin: String?,
        cutterDiamMax: String?,
        outerDiameterMin: String?,
        outerDiameterMax: String?,
        drivingTorqueMin: String?,
        drivingTorqueMax: String?,
        drivingPowerMin: String?,
        drivingPowerMax: String?,
        propulsiveForceMin: String?,
        propulsiveForceMax: String?,
        openingRateMin: String?,
        openingRateMax: String?,
        turningRadiusMin: String?,
        turningRadiusMax: String?,
        beamNumMin: String?,
        beamNumMax: String?
    ) {
        viewModelScope.launch {
            val param = getSearchParamOfDevice(
                deviceBrand,
                deviceType,
                cutterType,
                deviceArea,
                deviceStatus,
                cutterDiamMin,
                cutterDiamMax,
                outerDiameterMin,
                outerDiameterMax,
                drivingTorqueMin,
                drivingTorqueMax,
                drivingPowerMin,
                drivingPowerMax,
                propulsiveForceMin,
                propulsiveForceMax,
                openingRateMin,
                openingRateMax,
                turningRadiusMin,
                turningRadiusMax,
                beamNumMin,
                beamNumMax
            )
            val result = NetworkApi.requestOfQueryData(param)
            searchDeviceLiveData.value = result
        }
    }

    fun requestOfQueryDataOfRequire(
        deviceBrand: String?,
        deviceType: String?,
        cutterType: String?,
        projectLocation: String?,
        geologicalInfo: String?,
        cutterDiamMin: String?,
        cutterDiamMax: String?,
        outerDiameterMin: String?,
        outerDiameterMax: String?,
        drivingTorqueMin: String?,
        drivingTorqueMax: String?,
        usageTime: String?,
        projectLengthMin: String?,
        projectLengthMax: String?,
        propulsiveForceMin: String?,
        propulsiveForceMax: String?,
        openingRateMin: String?,
        openingRateMax: String?,
        turningRadiusMin: String?,
        turningRadiusMax: String?,
        demandNumMin: String?,
        demandNumMax: String?
    ) {
        viewModelScope.launch {
            val param = getSearchParamOfRequire(
                deviceBrand,
                deviceType,
                cutterType,
                projectLocation,
                geologicalInfo,
                cutterDiamMin,
                cutterDiamMax,
                outerDiameterMin,
                outerDiameterMax,
                drivingTorqueMin,
                drivingTorqueMax,
                usageTime,
                projectLengthMin,
                projectLengthMax,
                propulsiveForceMin,
                propulsiveForceMax,
                openingRateMin,
                openingRateMax,
                turningRadiusMin,
                turningRadiusMax,
                demandNumMin,
                demandNumMax
            )
            val result = NetworkApi.requestOfQueryData(param)
            searchRequireLiveData.value = result
        }
    }

    private fun getSearchParam(
        type: Int,
        key: String,
        value: String?
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = 1
        map["pageSize"] = 50
        map["type"] = type
        map["keyWord"] = value
        map[key] = value
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getSearchParamOfDevice(
        deviceBrand: String?,
        deviceType: String?,
        cutterType: String?,
        deviceArea: String?,
        deviceStatus: String?,
        cutterDiamMin: String?,
        cutterDiamMax: String?,
        outerDiameterMin: String?,
        outerDiameterMax: String?,
        drivingTorqueMin: String?,
        drivingTorqueMax: String?,
        drivingPowerMin: String?,
        drivingPowerMax: String?,
        propulsiveForceMin: String?,
        propulsiveForceMax: String?,
        openingRateMin: String?,
        openingRateMax: String?,
        turningRadiusMin: String?,
        turningRadiusMax: String?,
        beamNumMin: String?,
        beamNumMax: String?
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = 1
        map["pageSize"] = 50
        map["type"] = "1"
        map["deviceBrand"] = deviceBrand
        map["deviceType"] = deviceType
        map["cutterType"] = cutterType
        map["deviceArea"] = deviceArea
        map["deviceStatus"] = deviceStatus
        map["cutterDiamMin"] = cutterDiamMin
        map["cutterDiamMax"] = cutterDiamMax
        map["outerDiameterMin"] = outerDiameterMin
        map["outerDiameterMax"] = outerDiameterMax
        map["drivingTorqueMin"] = drivingTorqueMin
        map["drivingTorqueMax"] = drivingTorqueMax
        map["drivingPowerMin"] = drivingPowerMin
        map["drivingPowerMax"] = drivingPowerMax
        map["propulsiveForceMin"] = propulsiveForceMin
        map["propulsiveForceMax"] = propulsiveForceMax
        map["openingRateMin"] = openingRateMin
        map["openingRateMax"] = openingRateMax
        map["turningRadiusMin"] = turningRadiusMin
        map["turningRadiusMax"] = turningRadiusMax
        map["beamNumMin"] = beamNumMin
        map["beamNumMax"] = beamNumMax
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getSearchParamOfRequire(
        deviceBrand: String?,
        deviceType: String?,
        cutterType: String?,
        projectLocation: String?,
        geologicalInfo: String?,
        cutterDiamMin: String?,
        cutterDiamMax: String?,
        outerDiameterMin: String?,
        outerDiameterMax: String?,
        drivingTorqueMin: String?,
        drivingTorqueMax: String?,
        usageTime: String?,
        projectLengthMin: String?,
        projectLengthMax: String?,
        propulsiveForceMin: String?,
        propulsiveForceMax: String?,
        openingRateMin: String?,
        openingRateMax: String?,
        turningRadiusMin: String?,
        turningRadiusMax: String?,
        demandNumMin: String?,
        demandNumMax: String?
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["pageNo"] = 1
        map["pageSize"] = 50
        map["type"] = "2"
        map["deviceBrand"] = deviceBrand
        map["deviceType"] = deviceType
        map["cutterType"] = cutterType
        map["projectLocation"] = projectLocation
        map["geologicalInfo"] = geologicalInfo
        map["cutterDiamMin"] = cutterDiamMin
        map["cutterDiamMax"] = cutterDiamMax
        map["outerDiameterMin"] = outerDiameterMin
        map["outerDiameterMax"] = outerDiameterMax
        map["drivingTorqueMin"] = drivingTorqueMin
        map["drivingTorqueMax"] = drivingTorqueMax
        map["usageTime"] = usageTime
        map["projectLengthMin"] = projectLengthMin
        map["projectLengthMax"] = projectLengthMax
        map["propulsiveForceMin"] = propulsiveForceMin
        map["propulsiveForceMax"] = propulsiveForceMax
        map["openingRateMin"] = openingRateMin
        map["openingRateMax"] = openingRateMax
        map["turningRadiusMin"] = turningRadiusMin
        map["turningRadiusMax"] = turningRadiusMax
        map["demandNumMin"] = demandNumMin
        map["demandNumMax"] = demandNumMax
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}