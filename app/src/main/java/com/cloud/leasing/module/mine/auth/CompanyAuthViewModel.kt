package com.cloud.leasing.module.mine.auth

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.CompanyFileBean
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


class CompanyAuthViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val authLiveData = MutableLiveData<Result<String>>()

    val fileLiveData = MutableLiveData<Result<CompanyFileBean>>()

    @PageName
    override fun getPageName() = PageName.COMPANYAUTH

    fun requestOfCompanyAuth(
        filePath: String?,
        fileName: String?,
        corporateName: String,
        legalPerson: String?,
        idNumber: String?,
        dutyParagraph: String?
    ) {
        viewModelScope.launch {
            val param = getCompanyAuthParam(
                filePath,
                fileName,
                corporateName,
                legalPerson,
                idNumber,
                dutyParagraph
            )
            val result = NetworkApi.requestOfCompanyAuth(param)
            authLiveData.value = result
        }
    }

    fun requestOfUploadFile(file: File) {
        viewModelScope.launch {
            val mfile = getUploadFile(file)
            val param = getUploadParam()
            val result = NetworkApi.requestOfUploadFile(param, mfile)
            fileLiveData.value = result
        }
    }

    private fun getUploadFile(file: File): MultipartBody.Part {
        val requestFile: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("mf", file.name, requestFile)
    }

    private fun getUploadParam(): RequestBody {
        return "80".toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    private fun getCompanyAuthParam(
        filePath: String?,
        fileName: String?,
        corporateName: String,
        legalPerson: String?,
        idNumber: String?,
        dutyParagraph: String?
    ): RequestBody {
        val map = mutableMapOf<String, Any?>()
        map["userId"] = userId
        map["fileType"] = 1
        map["filePath"] = filePath
        map["fileName"] = fileName
        map["corporateName"] = corporateName
        map["legalPerson"] = legalPerson
        map["idNumber"] = idNumber
        map["dutyParagraph"] = dutyParagraph
        val json = JSON.toJSONString(map)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }

}