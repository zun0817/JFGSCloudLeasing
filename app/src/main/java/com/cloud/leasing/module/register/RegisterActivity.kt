package com.cloud.leasing.module.register

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
import com.cloud.leasing.databinding.ActivityRegisterBinding
import com.gyf.immersionbar.ktx.immersionBar

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate),
    View.OnClickListener, View.OnFocusChangeListener {


    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var isShowOne = true

    private var isShowTwo = true

    private val viewModel: RegisterViewModel by viewModels()

    override fun getPageName() = PageName.REGISTER

    override fun swipeBackEnable() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.registerBtn.setOnClickListener(this)
        viewBinding.registerBackImg.setOnClickListener(this)
        viewBinding.registerLoginTv.setOnClickListener(this)
        viewBinding.registerProtocolTv.setOnClickListener(this)
        viewBinding.layoutRegisterEditIn.registerEyeImg1.setOnClickListener(this)
        viewBinding.layoutRegisterEditIn.registerEyeImg2.setOnClickListener(this)
        viewBinding.layoutRegisterEditIn.registerSmsTv.setOnClickListener(this)
        viewBinding.layoutRegisterEditIn.registerPhoneEdit.onFocusChangeListener = this
        viewBinding.layoutRegisterEditIn.registerPasswordEdit1.onFocusChangeListener = this
        viewBinding.layoutRegisterEditIn.registerPasswordEdit2.onFocusChangeListener = this
        viewBinding.layoutRegisterEditIn.registerSmsEdit.onFocusChangeListener = this
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
            R.id.register_back_img -> this.finish()
            R.id.register_eye_img1 -> {
                if (isShowOne) {
                    viewBinding.layoutRegisterEditIn.registerEyeImg1.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.layoutRegisterEditIn.registerPasswordEdit1.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.layoutRegisterEditIn.registerEyeImg1.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.layoutRegisterEditIn.registerPasswordEdit1.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShowOne = !isShowOne
            }
            R.id.register_eye_img2 -> {
                if (isShowTwo) {
                    viewBinding.layoutRegisterEditIn.registerEyeImg2.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.layoutRegisterEditIn.registerPasswordEdit2.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.layoutRegisterEditIn.registerEyeImg2.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.layoutRegisterEditIn.registerPasswordEdit2.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShowTwo = !isShowTwo
            }
            R.id.register_sms_tv -> {

            }
            R.id.register_login_tv -> {
                this.finish()
            }
            R.id.register_protocol_tv -> {

            }
            R.id.register_btn -> {
                
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.register_phone_edit -> {
                when {
                    hasFocus -> viewBinding.layoutRegisterEditIn.registerView1.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutRegisterEditIn.registerView1.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.register_password_edit1 -> {
                when {
                    hasFocus -> viewBinding.layoutRegisterEditIn.registerView2.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutRegisterEditIn.registerView2.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.register_password_edit2 -> {
                when {
                    hasFocus -> viewBinding.layoutRegisterEditIn.registerView3.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutRegisterEditIn.registerView3.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.register_sms_edit -> {
                when {
                    hasFocus -> viewBinding.layoutRegisterEditIn.registerView4.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutRegisterEditIn.registerView4.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
        }
    }
}