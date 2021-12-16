package com.cloud.leasing.module.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityRegisterBinding
import com.cloud.leasing.util.CountDownTimerUtils
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.isMobilPhone
import com.cloud.leasing.util.toast
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

    private var isShowOne = false

    private var isShowTwo = false

    private var phone = ""

    private var password1 = ""

    private var password2 = ""

    private var smscode = ""

    private var countDownTimer: CountDownTimerUtils? = null

    private val viewModel: RegisterViewModel by viewModels()

    override fun getPageName() = PageName.REGISTER

    override fun swipeBackEnable() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
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
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.registerBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRegisterEditIn.registerSmsTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRegisterEditIn.registerEyeImg1)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutRegisterEditIn.registerEyeImg2)

        viewBinding.layoutRegisterEditIn.registerPhoneEdit.addTextChangedListener(phoneTextWatcher)
        viewBinding.layoutRegisterEditIn.registerPasswordEdit1.addTextChangedListener(
            password1TextWatcher
        )
        viewBinding.layoutRegisterEditIn.registerPasswordEdit2.addTextChangedListener(
            password2TextWatcher
        )
        viewBinding.layoutRegisterEditIn.registerSmsEdit.addTextChangedListener(smscodeTextWatcher)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            smsCodeLiveData.observe(this@RegisterActivity, {
                "请查收短信".toast(this@RegisterActivity)
                countDownTimer = CountDownTimerUtils(
                    viewBinding.layoutRegisterEditIn.registerSmsTv,
                    60000,
                    1000
                )
                countDownTimer?.apply {
                    start()
                }
            })
            registerLiveData.observe(this@RegisterActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "注册成功，请登录".toast(this@RegisterActivity)
                        this@RegisterActivity.finish()
                    } else {
                        it.toString().toast(this@RegisterActivity)
                    }
                }.onSuccess {
                    "注册成功，请登录".toast(this@RegisterActivity)
                    this@RegisterActivity.finish()
                }
                viewBinding.registerLoadingview.visibility = View.GONE
            })
        }
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
            R.id.register_back_img, R.id.register_login_tv -> this.finish()
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
                if (!isMobilPhone(phone)) {
                    "手机号码不正确".toast(this)
                    return
                }
                viewModel.requestOfSmsCode(phone)
            }
            R.id.register_protocol_tv -> {

            }
            R.id.register_btn -> {
                if (password1 != password2) {
                    "密码输入不一致".toast(this)
                    return
                }
                viewModel.requestOfRegister(phone, password1, smscode)
                viewBinding.registerLoadingview.visibility = View.VISIBLE
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

    private val phoneTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            phone = s.toString().trim()
            viewBinding.registerBtn.isEnabled = s.toString().trim()
                .isNotBlank() && password1.isNotBlank() && password2.isNotBlank() && smscode.isNotBlank()
        }
    }

    private val password1TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            password1 = s.toString().trim()
            viewBinding.registerBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
                .isNotBlank() && password2.isNotBlank() && smscode.isNotBlank()
        }
    }

    private val password2TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            password2 = s.toString().trim()
            viewBinding.registerBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
                .isNotBlank() && password1.isNotBlank() && smscode.isNotBlank()
        }
    }

    private val smscodeTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            smscode = s.toString().trim()
            viewBinding.registerBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
                .isNotBlank() && password1.isNotBlank() && password2.isNotBlank()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.apply {
            cancel()
        }
    }

}