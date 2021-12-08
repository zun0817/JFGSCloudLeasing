package com.cloud.leasing.module.mine

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentMineBinding
import com.cloud.leasing.module.home.publish.PublishActivity
import com.cloud.leasing.module.mine.about.AboutActivity
import com.cloud.leasing.module.mine.auth.CompanyAuthActivity
import com.cloud.leasing.module.mine.follow.MineFollowActivity
import com.cloud.leasing.module.mine.modify.ModifyActivity
import com.gyf.immersionbar.ktx.immersionBar


class MineFragment : BaseFragment<FragmentMineBinding>(FragmentMineBinding::inflate),
    View.OnClickListener {

    private val viewModel: MineViewModel by viewModels()

    @PageName
    override fun getPageName() = PageName.MINE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.mineCollectLl.setOnClickListener(this)
        viewBinding.mineNeedLl.setOnClickListener(this)
        viewBinding.mineModifyCl.setOnClickListener(this)
        viewBinding.mineAboutCl.setOnClickListener(this)
        viewBinding.mineClearCl.setOnClickListener(this)
        viewBinding.mineQuitCl.setOnClickListener(this)
        viewBinding.mineAuthTv.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mine_collect_ll -> MineFollowActivity.startActivity(requireActivity())
            R.id.mine_need_ll -> PublishActivity.startActivity(requireActivity())
            R.id.mine_auth_tv -> CompanyAuthActivity.startActivity(requireActivity())
            R.id.mine_modify_cl -> ModifyActivity.startActivity(requireActivity())
            R.id.mine_about_cl -> AboutActivity.startActivity(requireActivity())
            R.id.mine_clear_cl -> {
            }
            R.id.mine_quit_cl -> {
            }
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initSystemBar()
    }
}