package com.cloud.leasing.module.main

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.alibaba.fastjson.JSON
import com.cloud.dialoglibrary.BaseDialog
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.*
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.constant.TabId
import com.cloud.leasing.databinding.ActivityMainBinding
import com.cloud.leasing.item.DeviceItemViewData
import com.cloud.leasing.module.device.DeviceFragment
import com.cloud.leasing.module.home.HomeFragment
import com.cloud.leasing.module.mine.MineFragment
import com.cloud.leasing.module.mine.auth.CompanyAuthActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.toast
import com.cloud.leasing.widget.TabIndicatorView
import com.cloud.popwindow.WheelView
import com.gyf.immersionbar.ktx.immersionBar
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.manager.DownloadManager


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var manager: DownloadManager? = null

    private lateinit var dialog: BaseDialog

    private lateinit var typeList: MutableList<DeviceType>

    private lateinit var brandList: MutableList<DeviceBrand>

    private lateinit var cutterList: MutableList<CutterType>

    private val viewModel: MainViewModel by viewModels()

    @PageName
    override fun getPageName() = PageName.MAIN

    override fun swipeBackEnable() = false

    @TabId
    private var currentTabId = TabId.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initDialog()
        initView()
        initTabs()
        viewModelObserve()
        setCurrentTab(currentTabId)
    }

    private fun initView() {
        viewModel.requestOfDeviceType()
        viewModel.requestOfUpdateVersion()
        viewBinding.mianLoadingview.visibility = View.VISIBLE
        userAuth= XKeyValue.getString(Constant.USER_AUTH, "0")
        if (userAuth == "0") dialog.show()
    }

    private fun viewModelObserve() {
        viewModel.apply {
            deviceTypeLiveData.observe(this@MainActivity, { it ->
                viewBinding.mianLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@MainActivity)
                }.onSuccess {
                    typeList = it.deviceType
                    brandList = it.deviceBrand
                    cutterList = it.cutterType
                    val typejson = JSON.toJSON(typeList).toString()
                    val brandjson = JSON.toJSON(brandList).toString()
                    val cutterjson = JSON.toJSON(cutterList).toString()
                    XKeyValue.putString(Constant.DEVICE_TYPE, typejson)
                    XKeyValue.putString(Constant.DEVICE_BRAND, brandjson)
                    XKeyValue.putString(Constant.DEVICE_CUTTER, cutterjson)
                }
            })
            updateVersionLiveData.observe(this@MainActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code != 200) {
                        it.toString().toast(this@MainActivity)
                    }
                }.onSuccess {
                    startUpdate(it)
                }
            })
        }
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_2178D1)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    private fun initTabs() {
        val tabs = listOf(
            Tab(
                TabId.HOME,
                getString(R.string.page_home),
                R.drawable.selector_btn_home,
                HomeFragment::class
            ),
            Tab(
                TabId.DEVICE,
                getString(R.string.page_device),
                R.drawable.selector_btn_gold,
                DeviceFragment::class
            ),
            Tab(
                TabId.MINE,
                getString(R.string.page_mine),
                R.drawable.selector_btn_mine,
                MineFragment::class
            )
        )

        viewBinding.fragmentTabHost.run {
            // ??????setup()???????????????FragmentManager???????????????????????????Fragment???????????????
            setup(this@MainActivity, supportFragmentManager, viewBinding.fragmentContainer.id)
            tabs.forEach {
                val (id, title, icon, fragmentClz) = it
                val tabSpec = newTabSpec(id).apply {
                    setIndicator(TabIndicatorView(this@MainActivity).apply {
                        viewBinding.tabIcon.setImageResource(icon)
                        viewBinding.tabTitle.text = title
                    })
                }
                addTab(tabSpec, fragmentClz.java, null)
            }

            setOnTabChangedListener { tabId ->
                currentTabId = tabId
            }
        }
    }

    private fun setCurrentTab(@TabId tabID: String) {
        viewBinding.fragmentTabHost.setCurrentTabByTag(tabID)
    }

    private fun initDialog(){
        val builder = BaseDialog.Builder(this)
        dialog = builder
            .setViewId(R.layout.layout_auth_popwindow)
            .setStyle(R.style.CommonDialog)
            .setWidthHeightpx(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setGravity(Gravity.CENTER)
            .setAnimation(R.style.DialogScaleAnim)
            .isOnTouchCanceled(false)
            .setPaddingdp(30, 10, 30, 10)
            .addViewOnClickListener(R.id.dialog_view_close) {
                dialog.dismiss()
            }.builder()
        val dialog_auth_tv = dialog.getView<TextView>(R.id.dialog_auth_tv)
        dialog_auth_tv.setOnClickListener {
            dialog.dismiss()
            CompanyAuthActivity.startActivity(this, "0")
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

    private fun startUpdate(){
        val builder = BaseDialog.Builder(this)
        dialog = builder
            .setViewId(R.layout.layout_update_version_popwindow)
            .setStyle(R.style.CommonDialog)
            .setWidthHeightpx(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            .setGravity(Gravity.CENTER)
            .setAnimation(R.style.DialogScaleAnim)
            .isOnTouchCanceled(false)
            .setPaddingdp(30, 10, 30, 10)
            .addViewOnClickListener(R.id.update_version_delay_tv) {
                dialog.dismiss()
            }.builder()
        val update_version_upgrade_tv = dialog.getView<TextView>(R.id.update_version_upgrade_tv)
        update_version_upgrade_tv.setOnClickListener {
            CompanyAuthActivity.startActivity(this, "2")
        }
    }

}