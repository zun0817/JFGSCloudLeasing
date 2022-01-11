package com.cloud.leasing.module.home.want

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.DeviceTypeBean
import com.cloud.leasing.bean.ProvinceBean
import com.cloud.leasing.bean.UploadFileBean
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

class AddRequireViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val pictureLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val deleteFileLiveData = MutableLiveData<Result<String>>()

    val deletePictureLiveData = MutableLiveData<Result<String>>()

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

    fun requestOfUploadFile(file: File, fileType: String) {
        viewModelScope.launch {
            val mfile = getUploadFile(file)
            val param = getUploadParam(fileType)
            val result = NetworkApi.requestOfUploadFile(param, mfile)
            fileLiveData.value = result
        }
    }

    fun requestOfUploadPicture(file: File, fileType: String) {
        viewModelScope.launch {
            val mfile = getUploadFile(file)
            val param = getUploadParam(fileType)
            val result = NetworkApi.requestOfUploadFile(param, mfile)
            pictureLiveData.value = result
        }
    }

    fun requestOfDeleteFile(filePath: String) {
        viewModelScope.launch {
            val param = getQueryProfileParam(filePath)
            val result = NetworkApi.requestOfDeleteFile(param)
            deleteFileLiveData.value = result
        }
    }

    fun requestOfDeletePicture(filePath: String) {
        viewModelScope.launch {
            val param = getQueryProfileParam(filePath)
            val result = NetworkApi.requestOfDeleteFile(param)
            deletePictureLiveData.value = result
        }
    }

    private fun getQueryProfileParam(filePath: String): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["path"] = filePath
        return map
    }

    private fun getUploadFile(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("mf", file.name, requestFile)
    }

    private fun getUploadParam(fileType: String): RequestBody {
        return fileType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
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
        limitedRange: String,
        demandFileList: MutableList<UploadFileBean>
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
                limitedRange,
                demandFileList
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
        limitedRange: String,
        demandFileList: MutableList<UploadFileBean>
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
        map["demandFileList"] = demandFileList
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}