package com.cloud.leasing.module.forget

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
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityForgetBinding
import com.cloud.leasing.persistence.XKeyValue
import com.cloud.leasing.util.CountDownTimerUtils
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.isMobilPhone
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class ForgetActivity : BaseActivity<ActivityForgetBinding>(ActivityForgetBinding::inflate),
    View.OnClickListener, View.OnFocusChangeListener {

    companion object {
        fun startActivity(activity: Activity, type: Int) {
            val intent = Intent()
            intent.putExtra("type", type)
            intent.setClass(activity, ForgetActivity::class.java)
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

    private val viewModel: ForgetViewModel by viewModels()

    override fun getPageName() = PageName.FORGET

    override fun swipeBackEnable() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        when(intent.getIntExtra("type", -1)){
            -1 -> viewBinding.forgetTv1.text = "重置密码"
            0 -> viewBinding.forgetTv1.text = "忘记密码"
            1 -> viewBinding.forgetTv1.text = "修改密码"
        }
        viewBinding.forgetBtn.setOnClickListener(this)
        viewBinding.forgetBackImg.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetEyeImg1.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetEyeImg2.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetSmsTv.setOnClickListener(this)
        viewBinding.layoutForgetEditIn.forgetPhoneEdit.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetPasswordEdit1.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetPasswordEdit2.onFocusChangeListener = this
        viewBinding.layoutForgetEditIn.forgetSmsEdit.onFocusChangeListener = this
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.forgetBackImg)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutForgetEditIn.forgetEyeImg1)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutForgetEditIn.forgetEyeImg2)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutForgetEditIn.forgetSmsTv)

        viewBinding.layoutForgetEditIn.forgetPhoneEdit.addTextChangedListener(phoneTextWatcher)
        viewBinding.layoutForgetEditIn.forgetPasswordEdit1.addTextChangedListener(
            password1TextWatcher
        )
        viewBinding.layoutForgetEditIn.forgetPasswordEdit2.addTextChangedListener(
            password2TextWatcher
        )
        viewBinding.layoutForgetEditIn.forgetSmsEdit.addTextChangedListener(smscodeTextWatcher)

        val phone = XKeyValue.getString(Constant.USER_PHONE)
        if (phone.isNotBlank()) {
            viewBinding.layoutForgetEditIn.forgetPhoneEdit.setText(phone)
            viewBinding.layoutForgetEditIn.forgetPhoneEdit.setSelection(phone.length)
        }
    }

    private fun viewModelObserve() {
        viewModel.apply {
            smsCodeLiveData.observe(this@ForgetActivity, {
                "请查收短信".toast(this@ForgetActivity)
                countDownTimer = CountDownTimerUtils(
                    viewBinding.layoutForgetEditIn.forgetSmsTv,
                    60000,
                    1000
                )
                countDownTimer?.apply {
                    start()
                }
            })
            resetLiveData.observe(this@ForgetActivity, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "重置成功，请登录".toast(this@ForgetActivity)
                        this@ForgetActivity.finish()
                    } else {
                        it.toString().toast(this@ForgetActivity)
                    }
                }.onSuccess {
                    "重置成功，请登录".toast(this@ForgetActivity)
                    this@ForgetActivity.finish()
                }
                viewBinding.forgetLoadingview.visibility = View.GONE
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
                if (!isMobilPhone(phone)) {
                    "手机号码不正确".toast(this)
                    return
                }
                viewModel.requestOfSmsCode(phone)
            }
            R.id.forget_btn -> {
                if (password1 != password2) {
                    "密码输入不一致".toast(this)
                    return
                }
                viewModel.requestOfResetPassword(phone, password1, smscode)
                viewBinding.forgetLoadingview.visibility = View.VISIBLE
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
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

    private val phoneTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            phone = s.toString().trim()
            viewBinding.forgetBtn.isEnabled = s.toString().trim()
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
            viewBinding.forgetBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
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
            viewBinding.forgetBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
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
            viewBinding.forgetBtn.isEnabled = phone.isNotBlank() && s.toString().trim()
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