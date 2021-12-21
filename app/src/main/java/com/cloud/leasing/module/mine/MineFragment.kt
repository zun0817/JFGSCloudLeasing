package com.cloud.leasing.module.mine

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.cloud.dialoglibrary.*
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMineBinding
import com.cloud.leasing.module.forget.ForgetActivity
import com.cloud.leasing.module.home.publish.PublishActivity
import com.cloud.leasing.module.login.LoginActivity
import com.cloud.leasing.module.main.MainActivity
import com.cloud.leasing.module.mine.about.AboutActivity
import com.cloud.leasing.module.mine.auth.CompanyAuthActivity
import com.cloud.leasing.module.mine.follow.MineFollowActivity
import com.cloud.leasing.module.mine.profile.ProfileEditActivity
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.cleanInternalCache
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar


class MineFragment : BaseFragment<FragmentMineBinding>(FragmentMineBinding::inflate),
    View.OnClickListener {

    private val viewModel: MineViewModel by viewModels()

    private var isAttestation = "0"

    @PageName
    override fun getPageName() = PageName.MINE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestOfMineInfo()
    }

    private fun initView() {
        viewBinding.mineCollectLl.setOnClickListener(this)
        viewBinding.mineNeedLl.setOnClickListener(this)
        viewBinding.mineModifyCl.setOnClickListener(this)
        viewBinding.mineAboutCl.setOnClickListener(this)
        viewBinding.mineClearCl.setOnClickListener(this)
        viewBinding.mineQuitCl.setOnClickListener(this)
        viewBinding.mineAuthTv.setOnClickListener(this)
        viewBinding.mineProfileImg.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.mineProfileImg)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            logoutLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    when (it) {
                        0 -> "登出失败".toast(requireActivity())
                        2 -> "用户未登录".toast(requireActivity())
                        1 -> {
                            "登出成功".toast(requireActivity())
                            LoginActivity.startActivity(requireActivity())
                            (requireActivity() as MainActivity).finish()
                        }
                    }
                }
            })
            mineInfoLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    viewBinding.mineNameTv.text = it.mineUserName.toString()
                    viewBinding.mineIssueQtyTv.text = it.mineIssue.toString()
                    viewBinding.mineFollowQtyTv.text = it.mineFollowQty.toString()
                    isAttestation = it.isAttestation
                    when (it.isAttestation) {
                        "0" -> {
                            viewBinding.mineAuthTv.text = "未认证"
                            val spanString = SpannableString("您还没有企业认证")
                            val span = ForegroundColorSpan(resources.getColor(R.color.color_fcff00))
                            spanString.setSpan(span, 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            viewBinding.mineAuthTxtTv.text = spanString
                        }
                        "1" -> {
                            viewBinding.mineAuthTv.text = "已认证"
                            viewBinding.mineAuthTxtTv.text = it.mineCompany.toString()
                            viewBinding.mineAuthTxtTv.setTextColor(resources.getColor(R.color.white))
                        }
                        "2" -> {
                            viewBinding.mineAuthTv.text = "审核中"
                            val spanString = SpannableString("认证中，请耐心等待")
                            val span = ForegroundColorSpan(resources.getColor(R.color.color_fcff00))
                            spanString.setSpan(span, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                            viewBinding.mineAuthTxtTv.text = spanString
                            viewBinding.mineAuthTxtTv.setTextColor(resources.getColor(R.color.white))
                        }
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_collect_ll -> MineFollowActivity.startActivity(requireActivity())
            R.id.mine_need_ll -> PublishActivity.startActivity(requireActivity())
            R.id.mine_auth_tv -> {
                if (isAttestation == "1") {
                    "企业认证已成功".toast(requireActivity())
                    return
                }
                CompanyAuthActivity.startActivity(requireActivity(), isAttestation)
            }
            R.id.mine_profile_img -> ProfileEditActivity.startActivity(requireActivity())
            R.id.mine_modify_cl -> ForgetActivity.startActivity(requireActivity(), 1)
            R.id.mine_about_cl -> AboutActivity.startActivity(requireActivity())
            R.id.mine_clear_cl -> showCleanDialog()
            R.id.mine_quit_cl -> showQuitDialog()
        }
    }

    private fun initSystemBar() {
        immersionBar {
            statusBarColor(R.color.color_CFE9FA)
            fitsSystemWindows(true)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white)
            navigationBarDarkIcon(true)
        }
    }

    private fun showQuitDialog() {
        AwesomeDialog.build(requireActivity())
            .title(
                "提示",
                titleColor = ContextCompat.getColor(requireActivity(), R.color.color_262626)
            )
            .body(
                "确定退出登录吗？",
                color = ContextCompat.getColor(requireActivity(), R.color.color_262626)
            )
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(
                "确定",
                buttonBackgroundColor = R.drawable.shape_dialog_positive_0e64bc,
                textColor = ContextCompat.getColor(requireActivity(), android.R.color.white)
            ) {
                viewModel.requestOfLogout()
            }
            .onNegative(
                "取消",
                buttonBackgroundColor = R.drawable.shape_dialog_negative_0e64bc,
                textColor = ContextCompat.getColor(requireActivity(), android.R.color.black)
            )
    }

    private fun showCleanDialog() {
        AwesomeDialog.build(requireActivity())
            .title(
                "提示",
                titleColor = ContextCompat.getColor(requireActivity(), R.color.color_262626)
            )
            .body(
                "确定需要清除缓存吗？",
                color = ContextCompat.getColor(requireActivity(), R.color.color_262626)
            )
            .position(AwesomeDialog.POSITIONS.CENTER)
            .onPositive(
                "确定",
                buttonBackgroundColor = R.drawable.shape_dialog_positive_0e64bc,
                textColor = ContextCompat.getColor(requireActivity(), android.R.color.white)
            ) {
                cleanInternalCache(requireActivity())
            }
            .onNegative(
                "取消",
                buttonBackgroundColor = R.drawable.shape_dialog_negative_0e64bc,
                textColor = ContextCompat.getColor(requireActivity(), android.R.color.black)
            )
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initSystemBar()
    }
}