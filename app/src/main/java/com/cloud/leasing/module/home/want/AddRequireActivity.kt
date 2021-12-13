package com.cloud.leasing.module.home.want

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cloud.leasing.R
import com.cloud.leasing.adapter.AddRequirePictureAdapter
import com.cloud.leasing.base.BaseActivity
import com.cloud.leasing.constant.PageName
import com.cloud.leasing.databinding.ActivityAddRequireBinding
import com.cloud.leasing.util.ViewTouchUtil
import com.giftedcat.picture.lib.selector.MultiImageSelector
import com.gyf.immersionbar.ktx.immersionBar
import com.sky.filepicker.upload.Constants
import com.sky.filepicker.upload.LocalUpdateActivity


class AddRequireActivity :
    BaseActivity<ActivityAddRequireBinding>(ActivityAddRequireBinding::inflate),
    View.OnClickListener {

    companion object {
        fun startActivity(activity: Activity) {
            val intent = Intent()
            intent.setClass(activity, AddRequireActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val REQUEST_IMAGE = 2

    private var mSelectList = mutableListOf<String>()

    private lateinit var addRequirePictureAdapter: AddRequirePictureAdapter

    override fun getPageName() = PageName.ADDREQUIRE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSystemBar()
        initView()
    }

    private fun initView() {
        viewBinding.addRequireNextBtn.setOnClickListener(this)
        viewBinding.addRequireBackImg.setOnClickListener(this)
        viewBinding.addRequirePreviousBtn.setOnClickListener(this)
        viewBinding.layoutAddRequireUpload.addRequireFileFl.setOnClickListener(this)
        ViewTouchUtil.expandViewTouchDelegate(viewBinding.addRequireBackImg)
        viewBinding.layoutAddRequireUpload.addRequirePictureRv.layoutManager =
            GridLayoutManager(this, 3)
        addRequirePictureAdapter = AddRequirePictureAdapter(
            this,
            mSelectList,
            viewBinding.layoutAddRequireUpload.addRequirePictureRv
        )
        addRequirePictureAdapter.setMaxSize(5)
        viewBinding.layoutAddRequireUpload.addRequirePictureRv.adapter = addRequirePictureAdapter
        addRequirePictureAdapter.setOnAddPicturesListener {
            pickImage()
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

    private fun pickImage() {
        val selector = MultiImageSelector.create(this)
        selector.showCamera(true)
        selector.count(5)
        selector.multi()
        selector.origin(mSelectList)
        selector.start(this, REQUEST_IMAGE)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            data?.let {
                val select: MutableList<String> =
                    it.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT) as MutableList<String>
                mSelectList.clear()
                mSelectList.addAll(select)
                addRequirePictureAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == Constants.UPLOAD_FILE_REQUEST && resultCode == Constants.UPLOAD_FILE_RESULT) {
            data?.let { it ->
                val list: MutableList<String> =
                    it.getStringArrayListExtra("pathList") as MutableList<String>
                list.forEach {
                    Log.d("地址：", it)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.add_require_back_img -> this.finish()
            R.id.add_require_file_fl -> {
                val intent = Intent(this, LocalUpdateActivity::class.java)
                intent.putExtra("maxNum", 3)
                startActivityForResult(intent, Constants.UPLOAD_FILE_REQUEST)
            }
            R.id.add_require_next_btn -> {
                if (viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility ==
                    View.VISIBLE
                ) {
                    viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addRequireCircleTv2.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addRequireTxtTv2.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addRequirePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addRequireView1.setBackgroundResource(R.drawable.shape_view_0e64bc)
                } else if (viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility == View.VISIBLE) {
                    viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility = View.GONE
                    viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                        View.GONE
                    viewBinding.layoutAddRequireUser.addRequireUserLl.visibility = View.VISIBLE
                    viewBinding.addRequireCircleTv3.setBackgroundResource(R.drawable.shape_circle_0e64bc)
                    viewBinding.addRequireTxtTv3.setTextColor(resources.getColor(R.color.color_0E64BC))
                    viewBinding.addRequirePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addRequireView2.setBackgroundResource(R.drawable.shape_view_0e64bc)
                    viewBinding.addRequireNextBtn.text = "确定"
                }
            }
            R.id.add_require_previous_btn -> {
                if (viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility ==
                    View.VISIBLE
                ) {
                    viewBinding.layoutAddRequireInfo.addRequireInfoScrollview.visibility = View.VISIBLE
                    viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                        View.GONE
                    viewBinding.addRequireCircleTv2.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                    viewBinding.addRequireTxtTv2.setTextColor(resources.getColor(R.color.color_BFBFBF))
                    viewBinding.addRequireView1.setBackgroundResource(R.drawable.shape_view_eaeaea)
                    viewBinding.addRequirePreviousBtn.visibility = View.GONE
                } else if (viewBinding.layoutAddRequireUser.addRequireUserLl.visibility == View.VISIBLE) {
                    viewBinding.layoutAddRequireUser.addRequireUserLl.visibility = View.GONE
                    viewBinding.layoutAddRequireUpload.addRequireUploadScrollview.visibility =
                        View.VISIBLE
                    viewBinding.addRequireCircleTv3.setBackgroundResource(R.drawable.shape_circle_e2e2e2)
                    viewBinding.addRequireTxtTv3.setTextColor(resources.getColor(R.color.color_BFBFBF))
                    viewBinding.addRequireView2.setBackgroundResource(R.drawable.shape_view_eaeaea)
                    viewBinding.addRequirePreviousBtn.visibility = View.VISIBLE
                    viewBinding.addRequireNextBtn.text = "下一步"
                }
            }
        }
    }
}