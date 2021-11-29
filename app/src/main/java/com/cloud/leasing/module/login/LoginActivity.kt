package com.cloud.leasing.module.login

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
import com.cloud.leasing.databinding.ActivityLoginBinding
import com.cloud.leasing.module.forget.ForgetActivity
import com.cloud.leasing.module.main.MainActivity
import com.cloud.leasing.module.register.RegisterActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    View.OnClickListener, View.OnFocusChangeListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private var isShow = true

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    override fun getPageName() = PageName.LOGIN

    override fun swipeBackEnable() = false

    private fun initView() {
        viewBinding.loginPasswordTv.setOnClickListener(this)
        viewBinding.loginMessageTv.setOnClickListener(this)
        viewBinding.loginBtn.setOnClickListener(this)
        viewBinding.loginForgetTv.setOnClickListener(this)
        viewBinding.loginRegisterTv.setOnClickListener(this)
        viewBinding.loginProtocolTv.setOnClickListener(this)
        viewBinding.layoutPasswordEditLoginIn.loginEyeImg.setOnClickListener(this)
        viewBinding.layoutPasswordEditLoginIn.loginSmsImg1.setOnClickListener(this)
        viewBinding.layoutMessageEditLoginIn.loginSmsImg2.setOnClickListener(this)
        viewBinding.layoutMessageEditLoginIn.loginGetsmsTv.setOnClickListener(this)
        viewBinding.layoutPasswordEditLoginIn.loginPhoneEdit1.onFocusChangeListener = this
        viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.onFocusChangeListener = this
        viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.onFocusChangeListener = this
        viewBinding.layoutMessageEditLoginIn.loginMessageEdit.onFocusChangeListener = this
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_password_tv -> {  //密码登录
                viewBinding.loginPasswordTv.textSize = 18f
                viewBinding.loginMessageTv.textSize = 15f
                viewBinding.loginPasswordTv.setTextColor(resources.getColor(R.color.color_17191C))
                viewBinding.loginMessageTv.setTextColor(resources.getColor(R.color.color_999999))
                layout_password_edit_login_in.visibility = View.VISIBLE
                layout_message_edit_login_in.visibility = View.GONE
            }
            R.id.login_message_tv -> {  //短信登录
                viewBinding.loginPasswordTv.textSize = 15f
                viewBinding.loginMessageTv.textSize = 18f
                viewBinding.loginPasswordTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.loginMessageTv.setTextColor(resources.getColor(R.color.color_17191C))
                layout_password_edit_login_in.visibility = View.GONE
                layout_message_edit_login_in.visibility = View.VISIBLE
            }
            R.id.login_eye_img -> {  //密码显示与隐藏
                if (isShow) {
                    viewBinding.layoutPasswordEditLoginIn.loginEyeImg.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.layoutPasswordEditLoginIn.loginEyeImg.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShow = !isShow
            }
            R.id.login_sms_img1 -> {  //图形验证码

            }
            R.id.login_sms_img2 -> {  //图形验证码

            }
            R.id.login_getsms_tv -> {  //获取验证码

            }
            R.id.login_btn -> {  //登录
                MainActivity.startActivity(this)
            }
            R.id.login_forget_tv -> {  //忘记密码
                ForgetActivity.startActivity(this)
            }
            R.id.login_register_tv -> {  //免费注册
                RegisterActivity.startActivity(this)
            }
            R.id.login_protocol_tv -> {  //用户协议

            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.login_phone_edit1 -> {
                when {
                    hasFocus -> viewBinding.layoutPasswordEditLoginIn.loginPasswordView1.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutPasswordEditLoginIn.loginPasswordView1.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.login_password_edit -> {
                when {
                    hasFocus -> viewBinding.layoutPasswordEditLoginIn.loginPasswordView2.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutPasswordEditLoginIn.loginPasswordView2.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.login_phone_edit2 -> {
                when {
                    hasFocus -> viewBinding.layoutMessageEditLoginIn.loginMessageView1.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutMessageEditLoginIn.loginMessageView1.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.login_message_edit -> {
                when {
                    hasFocus -> viewBinding.layoutMessageEditLoginIn.loginMessageView2.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.layoutMessageEditLoginIn.loginMessageView2.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
        }
    }
}