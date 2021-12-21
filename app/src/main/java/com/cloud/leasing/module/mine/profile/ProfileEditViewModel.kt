package com.cloud.leasing.module.mine.profile

import androidx.lifecycle.viewModelScope
import com.cloud.leasing.base.BaseViewModel
import com.cloud.leasing.bean.UserInfoBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.eventbus.core.MutableLiveData
import com.cloud.leasing.network.NetworkApi
import com.cloud.leasing.persistence.XKeyValue
import kotlinx.coroutines.launch

class ProfileEditViewModel : BaseViewModel() {

    private val userId = XKeyValue.getInt(Constant.USER_ID)

    val profileLiveData = MutableLiveData<Result<UserInfoBean>>()

    @PageName
    override fun getPageName() = PageName.PROFILE_EDIT

    fun requestOfQueryProfile() {
        viewModelScope.launch {
            val param = getQueryProfileParam()
            val result = NetworkApi.requestOfQueryProfile(param)
            profileLiveData.value = result
        }
    }

    private fun getQueryProfileParam(): MutableMap<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["userId"] = userId
        return map
    }
}