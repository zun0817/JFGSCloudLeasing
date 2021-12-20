package com.cloud.leasing.module.mine.auth

import androidx.lifecycle.viewModelScope
import com.alibaba.fastjson.JSON
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CompanyAuthViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val authLiveData = MutableLiveData<Result<String>>()

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