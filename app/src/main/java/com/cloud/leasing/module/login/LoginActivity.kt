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
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityLoginBinding
import com.cloud.leasing.module.forget.ForgetActivity
import com.cloud.leasing.module.main.MainActivity
import com.cloud.leasing.module.register.RegisterActivity
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.*
import com.gyf.immersionbar.ktx.immersionBar
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
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

    private var isShow = false

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
        initPermission()
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
        viewBinding.layoutMessageEditLoginIn.loginGetsmsTv.setOnClickListener(this)
        viewBinding.layoutPasswordEditLoginIn.loginPhoneEdit.onFocusChangeListener = this
        viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.onFocusChangeListener = this
        viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.onFocusChangeListener = this
        viewBinding.layoutMessageEditLoginIn.loginMessageEdit.onFocusChangeListener = this
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.loginForgetTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.loginRegisterTv)
        val phone = XKeyValue.getString(Constant.USER_PHONE)
        val password = XKeyValue.getString(Constant.USER_PASSWORD)
        if (phone.isNotBlank()) {
            viewBinding.layoutPasswordEditLoginIn.loginPhoneEdit.setText(phone)
            viewBinding.layoutPasswordEditLoginIn.loginPhoneEdit.setSelection(phone.length)

            viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.setText(phone)
            viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.setSelection(phone.length)
        }
        if (password.isNotBlank()) {
            viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.setText(password)
            viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.setSelection(password.length)
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            smsCodeLiveData.observe(this@LoginActivity, {
                "???????????????".toast(this@LoginActivity)
                val countDownTimer = CountDownTimerUtils(
                    viewBinding.layoutMessageEditLoginIn.loginGetsmsTv,
                    60000,
                    1000
                )
                countDownTimer.start()
            })
            loginLiveData.observe(this@LoginActivity, { it ->
                viewBinding.LoginLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@LoginActivity)
                }.onSuccess {
                    "????????????".toast(this@LoginActivity)
                    XKeyValue.putInt(Constant.USER_ID, it.user.userId)
                    XKeyValue.putString(Constant.USER_TOKEN, it.token)
                    XKeyValue.putString(Constant.USER_PHONE, it.user.mobile)
                    XKeyValue.putString(Constant.USER_PASSWORD, it.user.password)
                    XKeyValue.putString(Constant.USER_AUTH, it.user.userAuth)
                    MainActivity.startActivity(this@LoginActivity)
                    this@LoginActivity.finish()
                }
            })
            messageLiveData.observe(this@LoginActivity, { it ->
                viewBinding.LoginLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@LoginActivity)
                }.onSuccess {
                    "????????????".toast(this@LoginActivity)
                    XKeyValue.putInt(Constant.USER_ID, it.user.userId)
                    XKeyValue.putString(Constant.USER_TOKEN, it.token)
                    XKeyValue.putString(Constant.USER_PHONE, it.user.mobile)
                    XKeyValue.putString(Constant.USER_AUTH, it.user.userAuth)
                    MainActivity.startActivity(this@LoginActivity)
                    this@LoginActivity.finish()
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.login_password_tv -> {  //????????????
                viewBinding.loginPasswordTv.textSize = 18f
                viewBinding.loginMessageTv.textSize = 15f
                viewBinding.loginPasswordTv.setTextColor(resources.getColor(R.color.color_17191C))
                viewBinding.loginMessageTv.setTextColor(resources.getColor(R.color.color_999999))
                layout_password_edit_login_in.visibility = View.VISIBLE
                layout_message_edit_login_in.visibility = View.GONE
            }
            R.id.login_message_tv -> {  //????????????
                viewBinding.loginPasswordTv.textSize = 15f
                viewBinding.loginMessageTv.textSize = 18f
                viewBinding.loginPasswordTv.setTextColor(resources.getColor(R.color.color_999999))
                viewBinding.loginMessageTv.setTextColor(resources.getColor(R.color.color_17191C))
                layout_password_edit_login_in.visibility = View.GONE
                layout_message_edit_login_in.visibility = View.VISIBLE
            }
            R.id.login_eye_img -> {  //?????????????????????
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
            R.id.login_getsms_tv -> {  //???????????????
                val phone =
                    viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.text.trim().toString()
                if (phone.isBlank()) {
                    "??????????????????".toast(this)
                    return
                }
                if (!isMobilPhone(phone)) {
                    "?????????????????????".toast(this)
                    return
                }
                viewModel.requestOfSmsCode(phone)
            }
            R.id.login_btn -> {  //??????
                if (viewBinding.layoutPasswordEditLoginIn.loginAccountLinear.visibility == View.VISIBLE) {
                    val phone =
                        viewBinding.layoutPasswordEditLoginIn.loginPhoneEdit.text.trim().toString()
                    val password =
                        viewBinding.layoutPasswordEditLoginIn.loginPasswordEdit.text.trim()
                            .toString()
                    if (phone.isBlank()) {
                        "??????????????????".toast(this)
                        return
                    }
                    if (!isMobilPhone(phone)) {
                        "?????????????????????".toast(this)
                        return
                    }
                    if (password.isBlank()) {
                        "???????????????".toast(this)
                        return
                    }
                    viewModel.requestOfLogin(phone, password)
                    viewBinding.LoginLoadingview.visibility = View.VISIBLE
                } else if (viewBinding.layoutMessageEditLoginIn.loginMessageLinear.visibility == View.VISIBLE) {
                    val phone =
                        viewBinding.layoutMessageEditLoginIn.loginPhoneEdit2.text.trim().toString()
                    val message =
                        viewBinding.layoutMessageEditLoginIn.loginMessageEdit.text.trim()
                            .toString()
                    if (phone.isBlank()) {
                        "??????????????????".toast(this)
                        return
                    }
                    if (!isMobilPhone(phone)) {
                        "?????????????????????".toast(this)
                        return
                    }
                    if (message.isBlank()) {
                        "????????????????????????".toast(this)
                        return
                    }
                    viewModel.requestOfLoginMessage(phone, message)
                    viewBinding.LoginLoadingview.visibility = View.VISIBLE
                    viewBinding.layoutMessageEditLoginIn.loginMessageEdit.hideKeyboard()
                }
            }
            R.id.login_forget_tv -> {  //????????????
                ForgetActivity.startActivity(this, 0)
            }
            R.id.login_register_tv -> {  //????????????
                RegisterActivity.startActivity(this)
            }
            R.id.login_protocol_tv -> {  //????????????
                PolicyActivity.startActivity(this, 1)
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.login_phone_edit -> {
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

    private fun initPermission() {
        XXPermissions.with(this).permission(Permission.CAMERA).permission(Permission.Group.STORAGE)
            .permission(Permission.CALL_PHONE).permission(Permission.READ_PHONE_STATE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                    if (all) {
                        "?????????????????????????????????".toast(this@LoginActivity)
                    } else {
                        "?????????????????????????????????????????????????????????".toast(this@LoginActivity)
                    }
                }

                override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                    super.onDenied(permissions, never)
                    if (never) {
                        "????????????????????????????????????????????????????????????".toast(this@LoginActivity)
                    } else {
                        "?????????????????????????????????".toast(this@LoginActivity)
                    }
                }
            })
    }

}