package com.cloud.leasing.module.mine.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.cameralibrary.PhotoSelector
import com.cloud.dialoglibrary.*
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityProfileEditBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar
import java.io.File

class ProfileEditActivity :
    BaseActivity<ActivityProfileEditBinding>(ActivityProfileEditBinding::inflate),
    View.OnClickListener, PhotoSelector.PermissionCallback,
    PhotoSelector.ResultCallback<File> {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, ProfileEditActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private lateinit var photoSelector: PhotoSelector

    private lateinit var builder: PhotoSelector.Builder

    private val viewModel: ProfileEditViewModel by viewModels()

    override fun getPageName() = PageName.PROFILE_EDIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.profileEditBackImg.setOnClickListener(this)
        viewBinding.profileEditAvatarImg.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaQuitBtn.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaPhotoBtn.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaAlbumBtn.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.profileEditBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.profileEditAvatarImg)
        viewModel.requestOfQueryProfile()

        builder = PhotoSelector.Builder(this)
        builder.setPermissionCallback(this)
        photoSelector = builder.create(this)
        builder.setCompress(true)
        builder.setCompressImageSize(1080)
        builder.setCrop(true)
        builder.setCropAspect(1000, 1000)
        builder.setCropOutput(1000, 1000)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            profileLiveData.observe(this@ProfileEditActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@ProfileEditActivity)
                }.onSuccess {
                    viewBinding.profileEditNameTv.text = it.userName
                    Glide.with(this@ProfileEditActivity)
                        .load(Constant.BASE_FILE_URL + it.filePath)
                        .centerCrop()
                        .placeholder(R.mipmap.icon_mine_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewBinding.profileEditAvatarImg)
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.profile_edit_back_img -> this.finish()
            R.id.profile_edit_avatar_img -> viewBinding.layoutCaremaView.layoutCaremaCl.visibility =
                View.VISIBLE
            R.id.layout_carema_quit_btn -> viewBinding.layoutCaremaView.layoutCaremaCl.visibility =
                View.GONE
            R.id.layout_carema_photo_btn -> {
                photoSelector.toCamera()
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility =
                    View.GONE
            }
            R.id.layout_carema_album_btn -> {
                photoSelector.toGallery()
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility =
                    View.GONE
            }
        }
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

    override fun onImageResult(file: File?) {
        file?.absolutePath?.toast(this)
    }

    override fun onPermissionRationale(permissions: MutableList<String>?) {
        showDialog()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoSelector.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        photoSelector.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showDialog() {
        AwesomeDialog.build(this)
            .title(
                "提示",
                titleColor = ContextCompat.getColor(this, R.color.color_262626)
            )
            .body(
                "相机存储权限无法正常使用，是否前往应用设置开启权限？",
                color = ContextCompat.getColor(this, R.color.color_262626)
            )
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(
                "确定",
                buttonBackgroundColor = R.drawable.shape_dialog_positive_0e64bc,
                textColor = ContextCompat.getColor(this, android.R.color.white)
            ) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", this.packageName, null)
                startActivity(intent)
            }
            .onNegative(
                "取消",
                buttonBackgroundColor = R.drawable.shape_dialog_negative_0e64bc,
                textColor = ContextCompat.getColor(this, android.R.color.black)
            )
    }

}