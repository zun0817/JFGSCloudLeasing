package com.cloud.leasing.module.mine.about

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.alibaba.fastjson.JSON
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager
import com.cloud.leasing.BuildConfig
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.UpdateVersionBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAboutBinding
import com.cloud.leasing.module.login.PolicyActivity
import com.cloud.leasing.module.main.MainViewModel
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class AboutActivity : BaseActivity<ActivityAboutBinding>(ActivityAboutBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AboutActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var manager: DownloadManager? = null

    private val viewModel: AboutViewModel by viewModels()

    @PageName
    override fun getPageName() = PageName.ABOUT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.aboutBackImg.setOnClickListener(this)
        viewBinding.aboutLawCl.setOnClickListener(this)
        viewBinding.aboutUserAgreementCl.setOnClickListener(this)
        viewBinding.aboutUpdateVersionCl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.aboutBackImg)
        viewBinding.aboutVersionTv.text = "V" + BuildConfig.VERSION_NAME
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_0e62B8)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.about_back_img -> this.finish()
            R.id.about_law_cl -> PolicyActivity.startActivity(this, 2)
            R.id.about_user_agreement_cl -> PolicyActivity.startActivity(this, 1)
            R.id.about_update_version_cl -> viewModel.requestOfUpdateVersion()
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            updateVersionLiveData.observe(this@AboutActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code != 200) {
                        it.toString().toast(this@AboutActivity)
                    }
                }.onSuccess {
                    startUpdate(it)
                }
            })
        }
    }

    private fun startUpdate(updateVersionBean: UpdateVersionBean) {
        /*
         * ??????????????????????????????
         * ?????????
         */
        val configuration = UpdateConfiguration()
            //??????????????????
            .setEnableLog(true)
            //????????????????????????
            //.setHttpManager()
            //????????????????????????????????????
            .setJumpInstallPage(true)
            //??????????????????????????? (??????????????????demo???????????????)
            //.setDialogImage(R.drawable.ic_dialog)
            //?????????????????????
            //.setDialogButtonColor(Color.parseColor("#E743DA"))
            //?????????????????????????????????????????????????????????
            .setDialogProgressBarColor(Color.parseColor("#E743DA"))
            //???????????????????????????
            .setDialogButtonTextColor(Color.WHITE)
            //?????????????????????????????????
            .setShowNotification(true)
            //??????????????????????????????toast
            .setShowBgdToast(false)
            //??????????????????
            .setForcedUpgrade(false)
            //????????????????????????????????????
            .setShowBgdToast(true)
        //.setButtonClickListener(this) //???????????????????????????
        //.setOnDownloadListener(listenerAdapter)
        manager = DownloadManager.getInstance(this)
        manager!!.setApkName("????????????.apk")
            .setApkUrl(updateVersionBean.appUrl)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setShowNewerToast(true)
            .setConfiguration(configuration)
            .setApkVersionCode(updateVersionBean.appName.toInt())
            .setApkVersionName(updateVersionBean.appNo)
            .setApkSize("36.4")
            .setApkDescription(updateVersionBean.appDesc)
            .download()
    }

}