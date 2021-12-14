package com.cloud.leasing.module.home.have

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.*
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddDeviceBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.giftedcat.picture.lib.selector.MultiImageSelector
import com.gyf.immersionbar.ktx.immersionBar
import com.sky.filepicker.upload.Constants
import com.sky.filepicker.upload.LocalUpdateActivity

class AddDeviceActivity :
    BaseActivity<ActivityAddDeviceBinding>(ActivityAddDeviceBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddDeviceActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val REQUEST_IMAGE_DEVICE = 1

    private val REQUEST_IMAGE_MACHINE = 2

    private val REQUEST_IMAGE_HOST = 3

    private val REQUEST_IMAGE_CUTTER = 4

    private var mPathList = mutableListOf<String>()

    private var mDevicePicList = mutableListOf<String>()

    private var mMachinePicList = mutableListOf<String>()

    private var mHostPicList = mutableListOf<String>()

    private var mCutterPicList = mutableListOf<String>()

    private lateinit var addDeviceFileAdapter: AddDeviceFileAdapter

    private lateinit var addDevicePictureAdapter: AddDevicePictureAdapter

    private lateinit var addMachinePictureAdapter: AddMachinePictureAdapter

    private lateinit var addHostPictureAdapter: AddHostPictureAdapter

    private lateinit var addCutterPictureAdapter: AddCutterPictureAdapter


    override fun getPageName() = PageName.ADDDEVICE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.addDeviceBackImg.setOnClickListener(this)
        viewBinding.addDeviceNextBtn.setOnClickListener(this)
        viewBinding.addDevicePreviousBtn.setOnClickListener(this)
        viewBinding.layoutAddDeviceUpload.addDeviceFileFl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addDeviceBackImg)

        viewBinding.layoutAddDeviceUpload.addDeviceFileRv.layoutManager =
            LinearLayoutManager(this)
        addDeviceFileAdapter = AddDeviceFileAdapter(this, mPathList)
        viewBinding.layoutAddDeviceUpload.addDeviceFileRv.adapter = addDeviceFileAdapter

        initDeviceForm()
        initMachineForm()
        initHostForm()
        initCutterForm()
    }

    private fun initDeviceForm() {
        viewBinding.layoutAddDeviceUpload.addDevicePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addDevicePictureAdapter = AddDevicePictureAdapter(
            this,
            mDevicePicList,
            viewBinding.layoutAddDeviceUpload.addDevicePictureRv
        )
        addDevicePictureAdapter.setMaxSize(5)
        viewBinding.layoutAddDeviceUpload.addDevicePictureRv.adapter = addDevicePictureAdapter
        addDevicePictureAdapter.setOnAddPicturesListener {
            pickImageOfDevice()
        }
    }

    private fun initMachineForm() {
        viewBinding.layoutAddDeviceUpload.addMachinePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addMachinePictureAdapter = AddMachinePictureAdapter(
            this,
            mDevicePicList,
            viewBinding.layoutAddDeviceUpload.addMachinePictureRv
        )
        addMachinePictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addMachinePictureRv.adapter = addMachinePictureAdapter
        addMachinePictureAdapter.setOnAddPicturesListener {
            pickImageOfMachine()
        }
    }

    private fun initHostForm() {
        viewBinding.layoutAddDeviceUpload.addHostPictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addHostPictureAdapter = AddHostPictureAdapter(
            this,
            mDevicePicList,
            viewBinding.layoutAddDeviceUpload.addHostPictureRv
        )
        addHostPictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addHostPictureRv.adapter = addHostPictureAdapter
        addHostPictureAdapter.setOnAddPicturesListener {
            pickImageOfHost()
        }
    }

    private fun initCutterForm() {
        viewBinding.layoutAddDeviceUpload.addCutterPictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addCutterPictureAdapter = AddCutterPictureAdapter(
            this,
            mDevicePicList,
            viewBinding.layoutAddDeviceUpload.addCutterPictureRv
        )
        addCutterPictureAdapter.setMaxSize(3)
        viewBinding.layoutAddDeviceUpload.addCutterPictureRv.adapter = addCutterPictureAdapter
        addCutterPictureAdapter.setOnAddPicturesListener {
            pickImageOfCutter()
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

    private fun pickImageOfDevice() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(5)
        selector.multi()
        selector.origin(mDevicePicList)
        selector.start(this, REQUEST_IMAGE_DEVICE)
    }

    private fun pickImageOfMachine() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(3)
        selector.multi()
        selector.origin(mMachinePicList)
        selector.start(this, REQUEST_IMAGE_MACHINE)
    }

    private fun pickImageOfHost() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(3)
        selector.multi()
        selector.origin(mHostPicList)
        selector.start(this, REQUEST_IMAGE_HOST)
    }

    private fun pickImageOfCutter() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(3)
        selector.multi()
        selector.origin(mCutterPicList)
        selector.start(this, REQUEST_IMAGE_CUTTER)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_DEVICE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mDevicePicList.clear()
                mDevicePicList.addAll(select)
                addDevicePictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == REQUEST_IMAGE_MACHINE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mMachinePicList.clear()
                mMachinePicList.addAll(select)
                addMachinePictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == REQUEST_IMAGE_HOST && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mHostPicList.clear()
                mHostPicList.addAll(select)
                addHostPictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == REQUEST_IMAGE_CUTTER && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mCutterPicList.clear()
                mCutterPicList.addAll(select)
                addCutterPictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == Constants.UPLOAD_FILE_REQUEST && resultCode == Constants.UPLOAD_FILE_RESULT) {
            data?.let { it ->
                val list: MutableList<String> =
                    it.getStringArrayListExtra("pathList") as MutableList<String>
                mPathList.addAll(list)
                addDeviceFileAdapter.notifyDataSetChanged()
                list.forEach {
                    Log.d("地址：", it)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_device_back_img -> this.finish()
            R.id.add_device_file_fl -> {
                val intent = Intent(this, LocalUpdateActivity::class.java)
                intent.putExtra("maxNum", 1)
                startActivityForResult(intent, Constants.UPLOAD_FILE_REQUEST)
            }
            R.id.add_device_next_btn -> {
                if (viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility ==
                    View.VISIBLE
                ) {
                    viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addDeviceCircleTv2.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addDeviceTxtTv2.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addDevicePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addDeviceView1.setBackgroundResource(R.drawable.shape_view_0e64bc)
                } else if (viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility == View.VISIBLE) {
                    viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                        View.GONE
                    viewBinding.layoutAddDeviceUser.addDeviceUserLl.visibility = View.VISIBLE
                    viewBinding.addDeviceCircleTv3.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addDeviceTxtTv3.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addDevicePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addRequireView2.setBackgroundResource(R.drawable.shape_view_0e64bc)
                    viewBinding.addDeviceNextBtn.text = "确定"
                }
            }
            R.id.add_device_previous_btn -> {
                if (viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility ==
                    View.VISIBLE
                ) {
                    viewBinding.layoutAddDeviceInfo.addDeviceInfoScrollview.visibility =
                        View.VISIBLE
                    viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                        View.GONE
                    viewBinding.addDeviceCircleTv2.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                    viewBinding.addDeviceTxtTv2.setTextColor(resources.getColor(R.color.color_BFBFBF))
                    viewBinding.addDeviceView1.setBackgroundResource(R.drawable.shape_view_eaeaea)
                    viewBinding.addDevicePreviousBtn.visibility = View.GONE
                } else if (viewBinding.layoutAddDeviceUser.addDeviceUserLl.visibility == View.VISIBLE) {
                    viewBinding.layoutAddDeviceUser.addDeviceUserLl.visibility = View.GONE
                    viewBinding.layoutAddDeviceUpload.addDeviceUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addDeviceCircleTv3.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                    viewBinding.addDeviceTxtTv3.setTextColor(resources.getColor(R.color.color_BFBFBF))
                    viewBinding.addRequireView2.setBackgroundResource(R.drawable.shape_view_eaeaea)
                    viewBinding.addDevicePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addDeviceNextBtn.text = "下一步"
                }
            }
        }
    }


}