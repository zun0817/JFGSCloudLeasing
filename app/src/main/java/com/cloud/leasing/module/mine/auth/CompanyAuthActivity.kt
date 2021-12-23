package com.cloud.leasing.module.mine.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.cloud.cameralibrary.PhotoSelector
import com.cloud.dialoglibrary.*
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityCompanyAuthBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar
import java.io.File

class CompanyAuthActivity :
    BaseActivity<ActivityCompanyAuthBinding>(ActivityCompanyAuthBinding::inflate),
    View.OnClickListener, PhotoSelector.PermissionCallback,
    PhotoSelector.ResultCallback<File> {

    companion object {
        fun startActivity(activity: Activity, type: String) {
            val intent = Intent()
            intent.putExtra("type", type)
            intent.setClass(activity, CompanyAuthActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private lateinit var photoSelector: PhotoSelector

    private lateinit var builder: PhotoSelector.Builder

    private val viewModel: CompanyAuthViewModel by viewModels()

    override fun getPageName() = PageName.COMPANYAUTH

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }


    private fun initView() {
        when (intent.getStringExtra("type")) {
            "1" -> {
                viewBinding.layoutCompanyAuthSuccess.companyAuthSuccessLl.visibility =
                    View.GONE
                viewBinding.companyAuthFl.visibility = View.GONE
                viewBinding.layoutCompanyAuthEdit.companyAuthEditLl.visibility = View.GONE
                viewBinding.layoutCompanyAuthFail.companyAuthFailLl.visibility = View.VISIBLE
            }
            "2" -> {
                viewBinding.layoutCompanyAuthSuccess.companyAuthSuccessLl.visibility =
                    View.VISIBLE
                viewBinding.companyAuthFl.visibility = View.GONE
                viewBinding.layoutCompanyAuthEdit.companyAuthEditLl.visibility = View.GONE
            }
        }
        viewBinding.companyAuthBackImg.setOnClickListener(this)
        viewBinding.companyAuthCommitBtn.setOnClickListener(this)
        viewBinding.layoutCompanyAuthFail.companyAuthFailBtn.setOnClickListener(this)
        viewBinding.layoutCompanyAuthEdit.companyAuthCameraImg.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaAlbumBtn.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaPhotoBtn.setOnClickListener(this)
        viewBinding.layoutCaremaView.layoutCaremaQuitBtn.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.companyAuthBackImg)

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
            authLiveData.observe(this@CompanyAuthActivity, { it ->
                it.onFailure {
                    viewBinding.companyAuthLoadingview.visibility = View.GONE
                    if ((it as NetworkException).code == 200) {
                        viewBinding.layoutCompanyAuthSuccess.companyAuthSuccessLl.visibility =
                            View.VISIBLE
                        viewBinding.layoutCompanyAuthEdit.companyAuthEditLl.visibility = View.GONE
                        viewBinding.companyAuthFl.visibility = View.GONE
                    } else {
                        it.toString().toast(this@CompanyAuthActivity)
                    }
                }.onSuccess {
                    viewBinding.companyAuthLoadingview.visibility = View.GONE
                    viewBinding.layoutCompanyAuthSuccess.companyAuthSuccessLl.visibility =
                        View.VISIBLE
                    viewBinding.companyAuthFl.visibility = View.GONE
                    viewBinding.layoutCompanyAuthEdit.companyAuthEditLl.visibility = View.GONE
                }
            })
            fileLiveData.observe(this@CompanyAuthActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@CompanyAuthActivity)
                }.onSuccess {
                    "上传成功".toast(this@CompanyAuthActivity)
                }
            })
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.company_auth_back_img -> this.finish()
            R.id.company_auth_commit_btn -> {
                val corporateName =
                    viewBinding.layoutCompanyAuthEdit.companyAuthNameEt.text.trim().toString()
                val legalPerson =
                    viewBinding.layoutCompanyAuthEdit.companyAuthLegalEt.text.trim().toString()
                val idNumber =
                    viewBinding.layoutCompanyAuthEdit.companyAuthCardnoEt.text.trim().toString()
                val dutyParagraph =
                    viewBinding.layoutCompanyAuthEdit.companyAuthCreditcodeEt.text.trim().toString()
                val filePath = ""
                val fileName = ""
                viewModel.requestOfCompanyAuth(
                    filePath,
                    fileName,
                    corporateName,
                    legalPerson,
                    idNumber,
                    dutyParagraph
                )
                viewBinding.companyAuthLoadingview.visibility = View.VISIBLE
            }
            R.id.company_auth_fail_btn -> {
                viewBinding.layoutCompanyAuthEdit.companyAuthEditLl.visibility = View.VISIBLE
                viewBinding.companyAuthFl.visibility = View.VISIBLE
                viewBinding.layoutCompanyAuthFail.companyAuthFailLl.visibility = View.GONE
            }
            R.id.company_auth_camera_img -> {
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility = View.VISIBLE
            }
            R.id.layout_carema_quit_btn -> {
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility = View.GONE
            }
            R.id.layout_carema_photo_btn -> {
                photoSelector.toCamera()
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility = View.GONE
            }
            R.id.layout_carema_album_btn -> {
                photoSelector.toGallery()
                viewBinding.layoutCaremaView.layoutCaremaCl.visibility = View.GONE
            }
        }
    }

    override fun onImageResult(file: File?) {
        file?.absolutePath?.toast(this)
        viewBinding.layoutCompanyAuthEdit.companyAuthPictureImg.setImageURI(Uri.fromFile(file))
        file?.let {
            viewModel.requestOfUploadFile(it)
        }
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