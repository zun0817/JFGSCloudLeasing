package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddFaultViewModel : BaseViewModel() {

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    val deleteFileLiveData = MutableLiveData<Result<String>>()

    @PageName
    override fun getPageName() = PageName.ADD_FAULT


    fun requestOfUploadFile(file: File, fileType: String) {
        viewModelScope.launch {
            val mfile = getUploadFile(file)
            val param = getUploadParam(fileType)
            val result = NetworkApi.requestOfUploadFile(param, mfile)
            fileLiveData.value = result
        }
    }

    fun requestOfDeleteFile(filePath: String) {
        viewModelScope.launch {
            val param = getDeleteFileParam(filePath)
            val result = NetworkApi.requestOfDeleteFile(param)
            deleteFileLiveData.value = result
        }
    }

    private fun getDeleteFileParam(filePath: String): MutableMap<String, Any> {
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

}