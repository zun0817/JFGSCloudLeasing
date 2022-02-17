package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.AddMaintenanceBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityEditMaintenanceBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class EditMaintenanceActivity :
    BaseActivity<ActivityEditMaintenanceBinding>(ActivityEditMaintenanceBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, data: AddMaintenanceBean) {
            val intent = Intent()
            intent.setClass(activity, EditMaintenanceActivity::class.java)
            intent.putExtra("data", data)
            activity.startActivity(intent)
        }
    }

    private lateinit var addMaintenanceBean: AddMaintenanceBean

    override fun getPageName() = PageName.EDIT_MAINTENANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        addMaintenanceBean = intent.getSerializableExtra("data") as AddMaintenanceBean
        viewBinding.editMaintenanceBackImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.editMaintenanceBackImg)
        viewBinding.editMaintenanceDevicenameTv.text = addMaintenanceBean.mtnceName
        viewBinding.editMaintenanceDevicesystemTv.text = addMaintenanceBean.mtnceSystem
        viewBinding.editMaintenanceDevicesiteTv.text = addMaintenanceBean.mtncePosition
        viewBinding.editMaintenanceMatterTv.text = addMaintenanceBean.needingAttention
        Glide.with(this)
            .load(Constant.BASE_FILE_URL + addMaintenanceBean.refPicture)
            .centerCrop()
            .placeholder(R.mipmap.icon_launcher_round)
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(viewBinding.editMaintenanceImg)
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
        when (v!!.id) {
            R.id.edit_maintenance_back_img -> this.finish()
        }
    }
}