package com.cloud.leasing.module.mine.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityCompanyAuthBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar

class CompanyAuthActivity :
    BaseActivity<ActivityCompanyAuthBinding>(ActivityCompanyAuthBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity, type: String) {
            val intent = Intent()
            intent.putExtra("type", type)
            intent.setClass(activity, CompanyAuthActivity::class.java)
            activity.startActivity(intent)
        }
    }

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
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.companyAuthBackImg)
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
        }
    }

}