package com.cloud.leasing.module.device.resume.use

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.ManageFileBean
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.network.NetworkApi
import kotlinx.coroutines.launch

class ManageDataFileViewModel : BaseViewModel() {

    val manageFileLiveData = MutableLiveData<Result<MutableList<ManageFileBean>>>()

    @PageName
    override fun getPageName() = PageName.MANAGE_DATA_FILE

    fun requestOfManageFile(manageId: Int) {
        viewModelScope.launch {
            val result = NetworkApi.requestOfManageFile(manageId)
            manageFileLiveData.value = result
        }
    }
}