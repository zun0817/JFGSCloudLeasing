package com.cloud.leasing.module.home

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.cloud.leasing.JFGSApplication
import com.cloud.leasing.R
import com.cloud.leasing.adapter.SimpleAdapter
import com.cloud.leasing.base.BaseFragment
import com.cloud.leasing.bean.BannerBean
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.bean.HomeRequireBean
import com.cloud.leasing.bean.exception.NetworkException
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.FragmentHomeBinding
import com.cloud.leasing.module.home.detail.DeviceDetailActivity
import com.cloud.leasing.module.home.detail.RequireDetailActivity
import com.cloud.leasing.module.home.have.AddDeviceActivity
import com.cloud.leasing.module.home.have.HaveActivity
import com.cloud.leasing.module.home.more.MoreDeviceActivity
import com.cloud.leasing.module.home.more.MoreRequireActivity
import com.cloud.leasing.module.home.publish.PublishActivity
import com.cloud.leasing.module.home.service.ServiceActivity
import com.cloud.leasing.module.home.want.AddRequireActivity
import com.cloud.leasing.module.home.want.WantActivity
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.gyf.immersionbar.ktx.immersionBar
import java.util.*


@RequiresApi(Build.VERSION_CODES.M)
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    View.OnScrollChangeListener, View.OnClickListener {

    private var isFlag = 0

    private var isFollowOne = false

    private var isFollowTwo = false

    private lateinit var homeDeviceBeanOne: HomeDeviceBean

    private lateinit var homeDeviceBeanTwo: HomeDeviceBean

    private lateinit var homeRequireBeanOne: HomeRequireBean

    private lateinit var homeRequireBeanTwo: HomeRequireBean

    private var mPictureList: MutableList<Int> = ArrayList()

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initViewPager(list: MutableList<BannerBean>) {
        viewBinding.mainBannerView.setLifecycleRegistry(lifecycle)
            .setAdapter(SimpleAdapter())
            .setIndicatorHeight(6)
            .setIndicatorSliderGap(8)
            .setIndicatorSliderRadius(10)
            .setIndicatorSliderWidth(36)
            .create(list)
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

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private fun viewModelObserve() {
        viewModel.apply {
            bannerLiveData.observe(viewLifecycleOwner, { it ->
                viewBinding.homeLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    it.takeIf { it.size > 0 }?.apply {
                        initViewPager(this)
                    }
                }
            })
            deviceLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    it.takeIf { it.size > 0 }?.apply {
                        forEachIndexed { index, homeDeviceBean ->
                            when (index) {
                                0 -> {
                                    homeDeviceBeanOne = homeDeviceBean
                                    viewBinding.layoutDeviceItemOne.deviceItemNameTv.text =
                                        homeDeviceBean.deviceCity + "·" + homeDeviceBean.deviceBrandName
                                    viewBinding.layoutDeviceItemOne.deviceItemTypeTv.text =
                                        homeDeviceBean.deviceTypeName
                                    viewBinding.layoutDeviceItemOne.deviceItemDiameterTv.text =
                                        "管片外径 " + homeDeviceBean.outerDiameter + "m"
                                    if (homeDeviceBean.focusStatus == 0) {
                                        val drawableLeft: Drawable =
                                            requireActivity().resources.getDrawable(
                                                R.mipmap.icon_follow_no
                                            )
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                            drawableLeft,
                                            null, null, null
                                        )
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                            "关注"
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                            10
                                        isFollowOne = false
                                    } else {
                                        val drawableLeft: Drawable =
                                            requireActivity().resources.getDrawable(
                                                R.mipmap.icon_follow_yes
                                            )
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                            drawableLeft,
                                            null, null, null
                                        )
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                            "已关注"
                                        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                            10
                                        isFollowOne = true
                                    }
                                }
                                1 -> {
                                    homeDeviceBeanTwo = homeDeviceBean
                                    viewBinding.layoutDeviceItemTwo.deviceItemNameTv.text =
                                        homeDeviceBean.deviceCity + "·" + homeDeviceBean.deviceBrandName
                                    viewBinding.layoutDeviceItemTwo.deviceItemTypeTv.text =
                                        homeDeviceBean.deviceTypeName
                                    viewBinding.layoutDeviceItemTwo.deviceItemDiameterTv.text =
                                        "管片外径 " + homeDeviceBean.outerDiameter + "m"
                                    if (homeDeviceBean.focusStatus == 0) {
                                        val drawableLeft: Drawable =
                                            requireActivity().resources.getDrawable(
                                                R.mipmap.icon_follow_no
                                            )
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                            drawableLeft,
                                            null, null, null
                                        )
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                            "关注"
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                            10
                                        isFollowTwo = false
                                    } else {
                                        val drawableLeft: Drawable =
                                            requireActivity().resources.getDrawable(
                                                R.mipmap.icon_follow_yes
                                            )
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                            drawableLeft,
                                            null, null, null
                                        )
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                            "已关注"
                                        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                            10
                                        isFollowTwo = true
                                    }
                                }
                            }
                        }
                    }
                }
            })
            requiresLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    it.toString().toast(requireActivity())
                }.onSuccess {
                    it.takeIf { it.size > 0 }?.apply {
                        forEachIndexed { index, homeRequireBean ->
                            when (index) {
                                0 -> {
                                    homeRequireBeanOne = homeRequireBean
                                    viewBinding.layoutRequireItemOne.requireItemTypeTv.text =
                                        homeRequireBean.deviceTypeName
                                    viewBinding.layoutRequireItemOne.requireItemCityTv.text =
                                        homeRequireBean.demandCity
                                    viewBinding.layoutRequireItemOne.requireItemLengthTv.text =
                                        homeRequireBean.projectLength
                                    viewBinding.layoutRequireItemOne.requireItemProductTv.text =
                                        homeRequireBean.deviceBrandName
                                    viewBinding.layoutRequireItemOne.requireItemNumTv.text =
                                        homeRequireBean.demandNum
                                    viewBinding.layoutRequireItemOne.requireItemGeologyTv.text =
                                        homeRequireBean.geologicalInfo
                                    viewBinding.layoutRequireItemOne.requireItemTimeTv.text =
                                        homeRequireBean.usageTime
                                }
                                1 -> {
                                    homeRequireBeanTwo = homeRequireBean
                                    viewBinding.layoutRequireItemTwo.requireItemTypeTv.text =
                                        homeRequireBean.deviceTypeName
                                    viewBinding.layoutRequireItemTwo.requireItemCityTv.text =
                                        homeRequireBean.demandCity
                                    viewBinding.layoutRequireItemTwo.requireItemLengthTv.text =
                                        homeRequireBean.projectLength
                                    viewBinding.layoutRequireItemTwo.requireItemProductTv.text =
                                        homeRequireBean.deviceBrandName
                                    viewBinding.layoutRequireItemTwo.requireItemNumTv.text =
                                        homeRequireBean.demandNum
                                    viewBinding.layoutRequireItemTwo.requireItemGeologyTv.text =
                                        homeRequireBean.geologicalInfo
                                    viewBinding.layoutRequireItemTwo.requireItemTimeTv.text =
                                        homeRequireBean.usageTime
                                }
                            }
                        }
                    }
                }
            })
            followLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "关注成功".toast(requireActivity())
                        when (isFlag) {
                            1 -> {
                                val drawableLeft: Drawable =
                                    requireActivity().resources.getDrawable(
                                        R.mipmap.icon_follow_yes
                                    )
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                    drawableLeft,
                                    null, null, null
                                )
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                    "已关注"
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                    10
                            }
                            2 -> {
                                val drawableLeft: Drawable =
                                    requireActivity().resources.getDrawable(
                                        R.mipmap.icon_follow_yes
                                    )
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                    drawableLeft,
                                    null, null, null
                                )
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                    "已关注"
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                    10
                            }
                        }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "关注成功".toast(requireActivity())
                    when (isFlag) {
                        1 -> {
                            val drawableLeft: Drawable =
                                requireActivity().resources.getDrawable(
                                    R.mipmap.icon_follow_yes
                                )
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                drawableLeft,
                                null, null, null
                            )
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                "已关注"
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                10
                        }
                        2 -> {
                            val drawableLeft: Drawable =
                                requireActivity().resources.getDrawable(
                                    R.mipmap.icon_follow_yes
                                )
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                drawableLeft,
                                null, null, null
                            )
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                "已关注"
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                10
                        }
                    }
                }
            })
            unfollowLiveData.observe(viewLifecycleOwner, { it ->
                it.onFailure {
                    if ((it as NetworkException).code == 200) {
                        "取消关注".toast(requireActivity())
                        when (isFlag) {
                            1 -> {
                                val drawableLeft: Drawable =
                                    requireActivity().resources.getDrawable(
                                        R.mipmap.icon_follow_no
                                    )
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                    drawableLeft,
                                    null, null, null
                                )
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                    "关注"
                                viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                    10
                            }
                            2 -> {
                                val drawableLeft: Drawable =
                                    requireActivity().resources.getDrawable(
                                        R.mipmap.icon_follow_no
                                    )
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                    drawableLeft,
                                    null, null, null
                                )
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                    "关注"
                                viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                    10
                            }
                        }
                    } else {
                        it.toString().toast(requireActivity())
                    }
                }.onSuccess {
                    "取消关注".toast(requireActivity())
                    when (isFlag) {
                        1 -> {
                            val drawableLeft: Drawable =
                                requireActivity().resources.getDrawable(
                                    R.mipmap.icon_follow_no
                                )
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                drawableLeft,
                                null, null, null
                            )
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.text =
                                "关注"
                            viewBinding.layoutDeviceItemOne.deviceItemFollowTv.compoundDrawablePadding =
                                10
                        }
                        2 -> {
                            val drawableLeft: Drawable =
                                requireActivity().resources.getDrawable(
                                    R.mipmap.icon_follow_no
                                )
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setCompoundDrawablesWithIntrinsicBounds(
                                drawableLeft,
                                null, null, null
                            )
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.text =
                                "关注"
                            viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.compoundDrawablePadding =
                                10
                        }
                    }
                }
            })
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.home_want_tv -> AddRequireActivity.startActivity(requireActivity())
            R.id.home_have_tv -> AddDeviceActivity.startActivity(requireActivity())
            R.id.home_service_tv -> ServiceActivity.startActivity(requireActivity())
            R.id.home_publish_tv -> PublishActivity.startActivity(requireActivity())
            R.id.main_device_more_tv -> MoreDeviceActivity.startActivity(requireActivity())
            R.id.main_require_more_tv -> MoreRequireActivity.startActivity(requireActivity())
        }
    }

    private fun getPicList(): MutableList<Int> {
        for (i in 0..3) {
            val drawable =
                resources.getIdentifier("icon_banner", "mipmap", requireActivity().packageName)
            mPictureList.add(drawable)
        }
        return mPictureList
    }

    private fun initView() {
        viewBinding.homeWantTv.setOnClickListener(this)
        viewBinding.homeHaveTv.setOnClickListener(this)
        viewBinding.homePublishTv.setOnClickListener(this)
        viewBinding.homeServiceTv.setOnClickListener(this)
        viewBinding.mainDeviceMoreTv.setOnClickListener(this)
        viewBinding.mainRequireMoreTv.setOnClickListener(this)
        viewBinding.layoutRequireItemOne.requireItemLookTv.setOnClickListener {
            RequireDetailActivity.startActivity(requireActivity())
        }
        viewBinding.layoutRequireItemTwo.requireItemLookTv.setOnClickListener {
            RequireDetailActivity.startActivity(requireActivity())
        }
        viewBinding.layoutDeviceItemOne.layoutDeviceItemCl.setOnClickListener {
            DeviceDetailActivity.startActivity(requireActivity())
        }
        viewBinding.layoutDeviceItemTwo.layoutDeviceItemCl.setOnClickListener {
            DeviceDetailActivity.startActivity(requireActivity())
        }
        viewBinding.layoutDeviceItemOne.deviceItemFollowTv.setOnClickListener {
            isFlag = 1
            when (isFollowOne) {
                false -> viewModel.requestOfAddFollow(homeDeviceBeanOne.id, "1")
                true -> viewModel.requestOfUnfollow(homeDeviceBeanOne.id, "0")
            }
        }
        viewBinding.layoutDeviceItemTwo.deviceItemFollowTv.setOnClickListener {
            isFlag = 2
            when (isFollowTwo) {
                false -> viewModel.requestOfAddFollow(homeDeviceBeanOne.id, "1")
                true -> viewModel.requestOfUnfollow(homeDeviceBeanOne.id, "0")
            }
        }
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceItemOne.deviceItemFollowTv)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.layoutDeviceItemTwo.deviceItemFollowTv)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            viewBinding.mainScrollview.setOnScrollChangeListener(this)
        }
        viewModel.requestOfBanner()
        viewModel.requestOfHomeDevices()
        viewModel.requestOfHomeRequires()
        viewBinding.homeLoadingview.visibility = View.VISIBLE
    }

    override fun onScrollChange(
        v: View?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        if (scrollY >= 150) {
            viewBinding.mainSearchImg.visibility = View.VISIBLE
        } else if (scrollY < 150) {
            viewBinding.mainSearchImg.visibility = View.GONE
        }
    }


    override fun onPause() {
        super.onPause()
        viewBinding.mainBannerView.stopLoop()
    }

    override fun onResume() {
        super.onResume()
        viewBinding.mainBannerView.startLoop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.mainBannerView.stopLoop()
    }

    @PageName
    override fun getPageName() = PageName.HOME

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        initSystemBar()
    }

}