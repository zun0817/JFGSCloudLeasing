package com.cloud.leasing.module.device.resume.use

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.R
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.bean.AddMaintenanceBean
import com.cloud.leasing.bean.CompanyFileBean
import com.cloud.leasing.constant.Constant
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityEditMaintenanceBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.cloud.leasing.util.toast
import com.giftedcat.picture.lib.selector.MultiImageSelector
import com.gyf.immersionbar.ktx.immersionBar
import java.io.File

class EditMaintenanceActivity :
    BaseActivity<ActivityEditMaintenanceBinding>(ActivityEditMaintenanceBinding::inflate),
    View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    companion object {
        fun startActivity(activity: Activity, data: AddMaintenanceBean) {
            val intent = Intent()
            intent.setClass(activity, EditMaintenanceActivity::class.java)
            intent.putExtra("data", data)
            activity.startActivityForResult(intent, Constant.REQUEST_CODE)
        }
    }

    private var isAbnormal = 0

    private var realPicture = ""

    private val REQUEST_IMAGE_DEVICE = 1

    private var fileBean: CompanyFileBean? = null

    private var mPicList = mutableListOf<String>()

    private lateinit var addMaintenanceBean: AddMaintenanceBean

    private val viewModel: EditMaintenanceViewModel by viewModels()

    override fun getPageName() = PageName.EDIT_MAINTENANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
        viewModelObserve()
    }

    private fun initView() {
        addMaintenanceBean = intent.getSerializableExtra("data") as AddMaintenanceBean
        viewBinding.editMaintenanceBackImg.setOnClickListener(this)
        viewBinding.editMaintenanceRg.setOnCheckedChangeListener(this)
        viewBinding.addPictureDeleteImg.setOnClickListener(this)
        viewBinding.addPictureSiv.setOnClickListener(this)
        viewBinding.editMaintenanceSubmitBtn.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.editMaintenanceBackImg)
        viewBinding.editMaintenanceDevicenameTv.text = addMaintenanceBean.mtnceName
        viewBinding.editMaintenanceDevicesystemTv.text = addMaintenanceBean.mtnceSystem
        viewBinding.editMaintenanceDevicesiteTv.text = addMaintenanceBean.mtncePosition
        viewBinding.editMaintenanceMatterTv.text = addMaintenanceBean.needingAttention
        Glide.with(this)
            .load(Constant.BASE_FILE_URL + addMaintenanceBean.refPicture)
            .centerCrop()
            .placeholder(R.mipmap.icon_launcher_round)
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(viewBinding.editMaintenanceImg)
    }

    private fun viewModelObserve() {
        viewModel.apply {
            fileLiveData.observe(this@EditMaintenanceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@EditMaintenanceActivity)
                }.onSuccess {
                    fileBean = it
                    realPicture = it.filePath
                    "上传成功".toast(this@EditMaintenanceActivity)
                    viewBinding.addPictureFl.visibility = View.VISIBLE
                    viewBinding.addPictureSiv.visibility = View.GONE
                    Glide.with(this@EditMaintenanceActivity)
                        .load(Constant.BASE_FILE_URL + it.filePath)
                        .centerCrop()
                        .placeholder(R.mipmap.icon_launcher_round)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(viewBinding.addPictureImg)
                }
            })
            deleteFileLiveData.observe(this@EditMaintenanceActivity, { it ->
                it.onFailure {
                    it.toString().toast(this@EditMaintenanceActivity)
                }.onSuccess {
                    "删除文件成功".toast(this@EditMaintenanceActivity)
                    viewBinding.addPictureFl.visibility = View.GONE
                    viewBinding.addPictureSiv.visibility = View.VISIBLE
                    viewBinding.addPictureImg.setImageResource(R.mipmap.icon_launcher_round)
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
            R.id.edit_maintenance_back_img -> this.finish()
            R.id.add_picture_siv -> pickFile()
            R.id.add_picture_delete_img -> viewModel.requestOfDeleteFile(fileBean!!.filePath)
            R.id.edit_maintenance_submit_btn -> {
                if (isAbnormal == 1){
                    if (viewBinding.editMaintenanceErrordescEt.text.trim().toString().isBlank()){
                        "请输入异常描述".toast(this)
                        return
                    }
                    if (realPicture.isBlank()){
                        "请上传图片".toast(this)
                        return
                    }
                }
                addMaintenanceBean.exceptionDetails = viewBinding.editMaintenanceErrordescEt.text.trim().toString()
                addMaintenanceBean.isAbnormal = isAbnormal
                addMaintenanceBean.realPicture = realPicture
                val intent = Intent()
                intent.putExtra("param", addMaintenanceBean)
                setResult(RESULT_OK, intent)
                this.finish()
            }
        }
    }

    private fun pickFile() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(1)
        selector.single()
        selector.origin(mPicList)
        selector.start(this, REQUEST_IMAGE_DEVICE)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.edit_maintenance_yes_rb -> {
                isAbnormal = 1
                viewBinding.editMaintenanceDescLinear.visibility = View.VISIBLE
                viewBinding.editMaintenanceExceptionLinear.visibility = View.VISIBLE
            }
            R.id.edit_maintenance_no_rb -> {
                isAbnormal = 0
                viewBinding.editMaintenanceDescLinear.visibility = View.GONE
                viewBinding.editMaintenanceExceptionLinear.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_DEVICE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mPicList.addAll(select)
                val file = File(select[0])
                viewModel.requestOfUploadFile(file, "90")
            }
        }
    }
}