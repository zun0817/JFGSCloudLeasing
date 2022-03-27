package com.cloud.leasing.network

import com.cloud.leasing.constant.Constant
import com.cloud.leasing.network.base.BaseNetworkApi
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * 网络请求具体实现
 * 需要部署服务端：http://123.52.43.250:2209
 */
object NetworkApi : BaseNetworkApi<INetworkService>(Constant.BASE_URL) {

    suspend fun requestVideoDetail(id: String) = getResult {
        service.requestVideoDetail(id)
    }

    suspend fun requestOfSmsCode(phoneNumber: String) = getResult {
        service.requestOfSmsCode(phoneNumber)
    }

    suspend fun requestOfRegister(requestBody: RequestBody) = getResult {
        service.requestOfRegister(requestBody)
    }

    suspend fun requestOfResetPassword(requestBody: RequestBody) = getResult {
        service.requestOfResetPassword(requestBody)
    }

    suspend fun requestOfLogin(requestBody: RequestBody) = getResult {
        service.requestOfLogin(requestBody)
    }

    suspend fun requestOfLoginMessage(requestBody: RequestBody) = getResult {
        service.requestOfLoginMessage(requestBody)
    }

    suspend fun requestOfQueryProfile(param: MutableMap<String, Any>) = getResult {
        service.requestOfQueryProfile(param)
    }

    suspend fun requestOfMineInfo(param: RequestBody) = getResult {
        service.requestOfMineInfo(param)
    }

    suspend fun requestOfLogout() = getResult {
        service.requestOfLogout()
    }

    suspend fun requestOfCompanyAuth(requestBody: RequestBody) = getResult {
        service.requestOfCompanyAuth(requestBody)
    }

    suspend fun requestOfDiviceFollow(requestBody: RequestBody) = getResult {
        service.requestOfDiviceFollow(requestBody)
    }

    suspend fun requestOfRequireFollow(requestBody: RequestBody) = getResult {
        service.requestOfRequireFollow(requestBody)
    }

    suspend fun requestOfAddFollow(requestBody: RequestBody) = getResult {
        service.requestOfAddFollow(requestBody)
    }

    suspend fun requestOfUnfollow(requestBody: RequestBody) = getResult {
        service.requestOfUnfollow(requestBody)
    }

    suspend fun requestOfMineDevice(requestBody: RequestBody) = getResult {
        service.requestOfMineDevice(requestBody)
    }

    suspend fun requestOfMineRequire(requestBody: RequestBody) = getResult {
        service.requestOfMineRequire(requestBody)
    }

    suspend fun requestOfUploadFile(fileType: RequestBody, file: MultipartBody.Part) = getResult {
        service.requestOfUploadFile(fileType, file)
    }

    suspend fun requestOfHomeDevices() = getResult {
        service.requestOfHomeDevices()
    }

    suspend fun requestOfHomeRequires() = getResult {
        service.requestOfHomeRequires()
    }

    suspend fun requestOfBanner() = getResult {
        service.requestOfBanner()
    }

    suspend fun requestOfNoticeService(requestBody: RequestBody) = getResult {
        service.requestOfNoticeService(requestBody)
    }

    suspend fun requestOfDeviceType() = getResult {
        service.requestOfDeviceType()
    }

    suspend fun requestOfDeviceSite() = getResult {
        service.requestOfDeviceSite()
    }

    suspend fun requestOfAddDevice(requestBody: RequestBody) = getResult {
        service.requestOfAddDevice(requestBody)
    }

    suspend fun requestOfAddRequire(requestBody: RequestBody) = getResult {
        service.requestOfAddRequire(requestBody)
    }

    suspend fun requestOfQueryData(requestBody: RequestBody) = getResult {
        service.requestOfQueryData(requestBody)
    }

    suspend fun requestOfDeviceDetail(deviceId: Int) = getResult {
        service.requestOfDeviceDetail(deviceId)
    }

    suspend fun requestOfRequireDetail(demandId: Int) = getResult {
        service.requestOfRequireDetail(demandId)
    }

    suspend fun requestOfDeleteFile(param: MutableMap<String, Any>) = getResult {
        service.requestOfDeleteFile(param)
    }

    suspend fun requestOfDeviceManagerForm(param: MutableMap<String, Any?>) = getResult {
        service.requestOfDeviceManagerForm(param)
    }

    suspend fun requestOfDeviceManageDetail(deviceId: Int) = getResult {
        service.requestOfDeviceManageDetail(deviceId)
    }

    suspend fun requestOfDeviceResume(deviceId: Int) = getResult {
        service.requestOfDeviceResume(deviceId)
    }

    suspend fun requestOfResumeDetail(resumeId: Int) = getResult {
        service.requestOfResumeDetail(resumeId)
    }

    suspend fun requestOfResumeProductDaily(resumeId: Int) = getResult {
        service.requestOfResumeProductDaily(resumeId)
    }

    suspend fun requestOfResumeFaultLedger(resumeId: Int) = getResult {
        service.requestOfResumeFaultLedger(resumeId)
    }

    suspend fun requestOfResumeMaintenance(resumeId: Int) = getResult {
        service.requestOfResumeMaintenance(resumeId)
    }

    suspend fun requestOfResumeFaultDaily(resumeId: Int) = getResult {
        service.requestOfResumeFaultDaily(resumeId)
    }

    suspend fun requestOfResumeStoreDaily(resumeId: Int) = getResult {
        service.requestOfResumeStoreDaily(resumeId)
    }

    suspend fun requestOfAddProductDaily(requestBody: RequestBody) = getResult {
        service.requestOfAddProductDaily(requestBody)
    }

    suspend fun requestOfManageData(resumeId: Int) = getResult {
        service.requestOfManageData(resumeId)
    }

    suspend fun requestOfManageFile(manageId: Int) = getResult {
        service.requestOfManageFile(manageId)
    }

    suspend fun requestOfAddFaultDaily(requestBody: RequestBody) = getResult {
        service.requestOfAddFaultDaily(requestBody)
    }

    suspend fun requestOfStoreCheck(resumeId: Int) = getResult {
        service.requestOfStoreCheck(resumeId)
    }

    suspend fun requestOfDownloadFile(fileName: String, path: String) = getResult {
        service.requestOfDownloadFile(fileName, path)
    }

    suspend fun requestOfLifeCycleFile(requestBody: RequestBody) = getResult {
        service.requestOfLifeCycleFile(requestBody)
    }

    suspend fun requestOfAddMaintenance(resumeId: Int) = getResult {
        service.requestOfAddMaintenance(resumeId)
    }

    suspend fun requestOfSubmitMaintenance(requestBody: RequestBody) = getResult {
        service.requestOfSubmitMaintenance(requestBody)
    }

    suspend fun requestOfSubmitDeposit(requestBody: RequestBody) = getResult {
        service.requestOfSubmitDeposit(requestBody)
    }

    suspend fun requestOfCityList(areaCode: String) = getResult {
        service.requestOfCityList(areaCode)
    }

    suspend fun requestOfUpdateVersion(appType: Int) = getResult {
        service.requestOfUpdateVersion(appType)
    }

}