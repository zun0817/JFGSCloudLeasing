package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.bean.DataUploadFileBean
import com.cloud.leasing.bean.ManageFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ManageDataFileViewModel : BaseViewModel() {

    val saveFileLiveData = MutableLiveData<Result<String>>()

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val manageFileLiveData = MutableLiveData<Result<MutableList<ManageFileBean>>>()

    @PageName
    override fun getPageName() = PageName.MANAGE_DATA_FILE

    fun requestOfManageFile(manageId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfManageFile(manageId)
            manageFileLiveData.value = result
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

    fun requestOfLifeCycleFile(
        manageId: Int,
        deviceManageFileList: MutableList<DataUploadFileBean>
    ) {
        viewModelScope.launch {
            val param = getLifeCycleFileParam(manageId, deviceManageFileList)
            val result = NetworkApi.requestOfLifeCycleFile(param)
            saveFileLiveData.value = result
        }
    }

    private fun getLifeCycleFileParam(
        manageId: Int,
        deviceManageFileList: MutableList<DataUploadFileBean>
    ): RequestBody {
        val map = mutableMapOf<String, Any>()
        map["manageId"] = manageId
        map["deviceManageFileList"] = deviceManageFileList
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

    private fun getUploadFile(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("mf", file.name, requestFile)
    }

    private fun getUploadParam(fileType: String): RequestBody {
        return fileType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }
}