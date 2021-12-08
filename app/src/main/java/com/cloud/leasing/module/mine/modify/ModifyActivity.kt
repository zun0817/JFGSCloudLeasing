package com.cloud.leasing.module.mine.modify

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityModifyBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.gyf.immersionbar.ktx.immersionBar

class ModifyActivity : BaseActivity<ActivityModifyBinding>(ActivityModifyBinding::inflate),
    View.OnClickListener, View.OnFocusChangeListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, ModifyActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getPageName() = PageName.MODIFY

    private var isShow_one = true

    private var isShow_two = true

    private var isShow_three = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.modifyBackImg.setOnClickListener(this)
        viewBinding.modifyBtn.setOnClickListener(this)
        viewBinding.modifyEyeImg1.setOnClickListener(this)
        viewBinding.modifyEyeImg2.setOnClickListener(this)
        viewBinding.modifyEyeImg3.setOnClickListener(this)
        viewBinding.modifyPasswordEdit1.onFocusChangeListener = this
        viewBinding.modifyPasswordEdit2.onFocusChangeListener = this
        viewBinding.modifyPasswordEdit3.onFocusChangeListener = this
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.modifyBackImg)
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
            R.id.modify_back_img -> this.finish()
            R.id.modify_eye_img1 -> {
                if (isShow_one) {
                    viewBinding.modifyEyeImg1.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.modifyPasswordEdit1.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.modifyEyeImg1.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.modifyPasswordEdit1.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShow_one = !isShow_one
            }
            R.id.modify_eye_img2 -> {
                if (isShow_two) {
                    viewBinding.modifyEyeImg2.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.modifyPasswordEdit2.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.modifyEyeImg2.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.modifyPasswordEdit2.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShow_two = !isShow_two
            }
            R.id.modify_eye_img3 -> {
                if (isShow_three) {
                    viewBinding.modifyEyeImg3.setImageResource(R.mipmap.icon_login_show)
                    viewBinding.modifyPasswordEdit3.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                } else {
                    viewBinding.modifyEyeImg3.setImageResource(R.mipmap.icon_login_eye)
                    viewBinding.modifyPasswordEdit3.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isShow_three = !isShow_three
            }
            R.id.modify_btn -> {
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when (v!!.id) {
            R.id.modify_password_edit1 -> {
                when {
                    hasFocus -> viewBinding.modifyPasswordView1.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.modifyPasswordView1.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.modify_password_edit2 -> {
                when {
                    hasFocus -> viewBinding.modifyPasswordView2.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.modifyPasswordView2.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
            R.id.modify_password_edit3 -> {
                when {
                    hasFocus -> viewBinding.modifyPasswordView3.setBackgroundColor(
                        resources.getColor(R.color.color_0E64BC)
                    )
                    else -> viewBinding.modifyPasswordView3.setBackgroundColor(
                        resources.getColor(R.color.color_F7F7F7)
                    )
                }
            }
        }
    }
}