package com.cloud.leasing.module.forget

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityForgetBinding
import com.gyf.immersionbar.ktx.immersionBar

class ForgetActivity : BaseActivity<ActivityForgetBinding>(ActivityForgetBinding::inflate),
    View.OnClickListener, View.OnFocusChangeListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, ForgetActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var isShowOne = true

    private var isShowTwo = true

    private val viewModel: ForgetViewModel by viewModels()

    override fun getPageName() = PageName.FORGET

    override fun swipeBackEnable() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.forgetBtn.setOnClickListener(this)
        viewBinding.forgetBackImg.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetEyeImg1.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetEyeImg2.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetSmsTv.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetPhoneEdit.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetPasswordEdit1.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetPasswordEdit2.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetSmsEdit.onFocusChangeListener = this
    }

    private fun initSystemBar() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.forget_back_img -> this.finish()
            R.id.forget_eye_img1 -> {
                if (isShowOne) {
                    viewBinding.layoutForgetEditIn.forgetEyeImg1.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.layoutForgetEditIn.forgetPasswordEdit1.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.layoutForgetEditIn.forgetEyeImg1.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.layoutForgetEditIn.forgetPasswordEdit1.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShowOne = !isShowOne
            }
            R.id.forget_eye_img2 -> {
                if (isShowTwo) {
                    viewBinding.layoutForgetEditIn.forgetEyeImg2.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.layoutForgetEditIn.forgetPasswordEdit2.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.layoutForgetEditIn.forgetEyeImg2.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.layoutForgetEditIn.forgetPasswordEdit2.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShowTwo = !isShowTwo
            }
            R.id.forget_sms_tv -> {

            }
            R.id.forget_btn -> {

            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when(v!!.id){
            R.id.forget_phone_edit -> {
                when {
                    hasFocus -> viewBinding.layoutForgetEditIn.forgetView1.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutForgetEditIn.forgetView1.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.forget_password_edit1 -> {
                when {
                    hasFocus -> viewBinding.layoutForgetEditIn.forgetView2.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutForgetEditIn.forgetView2.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.forget_password_edit2 -> {
                when {
                    hasFocus -> viewBinding.layoutForgetEditIn.forgetView3.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutForgetEditIn.forgetView3.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.forget_sms_edit -> {
                when {
                    hasFocus -> viewBinding.layoutForgetEditIn.forgetView4.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutForgetEditIn.forgetView4.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
        }
    }

}