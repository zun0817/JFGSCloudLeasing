package com.cloud.leasing.module.home.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.RequireDetailFileAdapter
import com.cloud.leasing.adapter.RequireDetailPictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.DemandFile
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityRequireDetailBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar

class RequireDetailActivity :
    BaseActivity<ActivityRequireDetailBinding>(ActivityRequireDetailBinding::inflate),
    View.OnClickListener, TabLayout.OnTabSelectedListener, NestedScrollView.OnScrollChangeListener {

    companion object {
        fun startActivity(activity: Activity, demandId: Int) {
            val intent = Intent()
            intent.setClass(activity, RequireDetailActivity::class.java)
            intent.putExtra("demandId", demandId)
            activity.startActivity(intent)
        }
    }

    private var tabIndex = 0

    private var scrollviewFlag = false

    private val fileList = mutableListOf<DemandFile>()

    private val pictureList = mutableListOf<DemandFile>()

    private lateinit var detailFileAdapter: RequireDetailFileAdapter

    private lateinit var detailPictureAdapter: RequireDetailPictureAdapter

    private val viewModel: RequireDetailViewModel by viewModels()

    override fun getPageName() = PageName.REQUIRE_DETAIL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        viewBinding.requireDetailBackImg.setOnClickListener(this)
        viewBinding.requireDetailScrollview.setOnScrollChangeListener(this)
        viewBinding.requireDetailTablayout.addOnTabSelectedListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.requireDetailBackImg)

        val demandId = intent.getIntExtra("demandId", 0)
        viewModel.requestOfRequireDetail(demandId)

        viewBinding.requireDetailLoadingview.visibility = View.VISIBLE

        val gridLayoutManager = GridLayoutManager(this, 3)
        viewBinding.layoutRequireDetailFile.requireDetailPictureRv.layoutManager = gridLayoutManager

        val linearLayoutManager = LinearLayoutManager(this)
        viewBinding.layoutRequireDetailFile.requireDetailFileRv.layoutManager = linearLayoutManager
    }

    private fun viewModelObserve() {
        viewModel.apply {
            requireDetailLiveData.observe(this@RequireDetailActivity, { it ->
                viewBinding.requireDetailLoadingview.visibility = View.GONE
                it.onFailure {
                    it.toString().toast(this@RequireDetailActivity)
                }.onSuccess { it ->
                    viewBinding.layoutRequireDetailInfo.requireDetailNameTv.text = it.deviceTypeName
                    viewBinding.layoutRequireDetailInfo.requireDetailDateTv.text = "创建时间："
                    it.createTime.split(" ")[0]
                    viewBinding.layoutRequireDetailInfo.requireDetailBrandTv.text =
                        it.deviceBrandName
                    viewBinding.layoutRequireDetailInfo.requireDetailTypeTv.text = it.deviceTypeName
                    viewBinding.layoutRequireDetailInfo.requireDetailPlaceTv.text = it.demandCity
                    viewBinding.layoutRequireDetailInfo.requireDetailDiameterTv.text =
                        if (it.cutterDiam.isBlank()) "暂无" else it.cutterDiam + "mm"
                    viewBinding.layoutRequireDetailInfo.requireDetailGeologyTv.text =
                        if (it.geologicalInfo.isBlank()) "暂无" else it.geologicalInfo
                    viewBinding.layoutRequireDetailInfo.requireDetailCountTv.text =
                        it.demandNum.toString() + "台"
                    viewBinding.layoutRequireDetailInfo.requireDetailUseTv.text = if (it.usageTime.isBlank()) "无" else it.usageTime
                    viewBinding.layoutRequireDetailInfo.requireDetailTorqueTv.text =
                        if (it.drivingTorque.isBlank()) "暂无" else it.drivingTorque + "KN·m"
                    viewBinding.layoutRequireDetailInfo.requireDetailLengthTv.text =
                        it.projectLength.toString()
                    viewBinding.layoutRequireDetailInfo.requireDetailPropulTv.text =
                        if (it.propulsiveForce.isBlank()) "暂无" else it.propulsiveForce
                    viewBinding.layoutRequireDetailInfo.requireDetailOuterTv.text =
                        if (it.outerDiameter.isBlank()) "暂无" else it.outerDiameter + "mm"
                    viewBinding.layoutRequireDetailInfo.requireDetailOpeningTv.text =
                        if (it.openingRate.isBlank()) "暂无" else it.openingRate + "%"
                    viewBinding.layoutRequireDetailInfo.requireDetailCuttertypeTv.text =
                        if (it.cutterTypeName.isBlank()) "暂无" else it.cutterTypeName
                    viewBinding.layoutRequireDetailInfo.requireDetailRadiusTv.text =
                        if (it.turningRadius.isBlank()) "暂无" else it.turningRadius + "m"
                    viewBinding.layoutRequireDetailUser.requireDetailUsernameTv.text = it.name
                    viewBinding.layoutRequireDetailUser.requireDetailPhoneTv.text = it.mobile
                    viewBinding.layoutRequireDetailUser.requireDetailPlaceTv.text =
                        it.corporateName ?: "暂无"
                    it.demandFileList.takeIf { it.isNotEmpty() }?.onEach {
                        when (it.fileType) {
                            "1" -> {
                                fileList.add(it)
                            }
                            else -> {
                                pictureList.add(it)
                            }
                        }
                    }
                    detailPictureAdapter =
                        RequireDetailPictureAdapter(
                            this@RequireDetailActivity,
                            pictureList
                        )
                    viewBinding.layoutRequireDetailFile.requireDetailPictureRv.adapter =
                        detailPictureAdapter
                    if (fileList.size > 0) {
                        viewBinding.layoutRequireDetailFile.requireDetailFileRv.visibility =
                            View.VISIBLE
                        detailFileAdapter =
                            RequireDetailFileAdapter(
                                this@RequireDetailActivity,
                                fileList
                            )
                        viewBinding.layoutRequireDetailFile.requireDetailFileRv.adapter =
                            detailFileAdapter
                    }
                    if (it.isExamine == "0") {
                        viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.text = "待审核"
                        viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.background =
                            resources.getDrawable(R.drawable.shape_want_label_13ad6b)
                    } else {
                        if (it.matchStatus == "1") {
                            viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.text = "已匹配"
                            viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.background =
                                resources.getDrawable(R.drawable.shape_want_label_0e64bc)
                        } else {
                            viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.text = "未匹配"
                            viewBinding.layoutRequireDetailInfo.requireDetailLabelTv.background =
                                resources.getDrawable(R.drawable.shape_want_label_ffaa33)
                        }
                    }
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
            R.id.require_detail_back_img -> this.finish()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (!scrollviewFlag) {
            when (tab.position) {
                0 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailInfoLl.top
                    )
                }
                1 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailAnnexLl.top
                    )
                }
                2 -> {
                    viewBinding.requireDetailScrollview.smoothScrollTo(
                        0,
                        viewBinding.requireDetailUserLl.top
                    )
                }
            }
        }
        scrollviewFlag = false
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
    }

    override fun onScrollChange(
        v: NestedScrollView,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        scrollviewFlag = true
        tabIndex = viewBinding.requireDetailTablayout.selectedTabPosition
        if (scrollY < viewBinding.requireDetailAnnexLl.top) {
            if (tabIndex != 0) {//增加判断，如果滑动的区域是tableIndex=0对应的区域，则不改变tablayout的状态
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        0
                    )
                )
            }
        } else if (scrollY >= viewBinding.requireDetailAnnexLl.top && scrollY < viewBinding.requireDetailUserLl.top) {
            if (tabIndex != 1) {
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        1
                    )
                )
            }
        } else if (scrollY >= viewBinding.requireDetailUserLl.top) {
            if (tabIndex != 2) {
                viewBinding.requireDetailTablayout.selectTab(
                    viewBinding.requireDetailTablayout.getTabAt(
                        2
                    )
                )
            }
        }
        scrollviewFlag = false
    }
}