package com.sky.filepicker.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.sky.filepicker.kotlin.equal
import com.sky.filepicker.model.FileBean
import com.sky.filepicker.model.RefreshUpLoadFragmentBean
import com.sky.filepicker.model.RemoveFileBean
import com.sky.filepicker.utils.Utils
import com.sky.filepicker.R
import kotlinx.android.synthetic.main.fragment_storage.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.io.File
import java.util.*


class StorageFragment : Fragment(), StorageAdapter.OnItemClickListener {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(StorageFragmentViewModel::class.java)
    }
    private var storageAdapter: StorageAdapter? = null
    private var storageFragment: StorageFragment? = null

    companion object {
        fun newInstance(path: String): StorageFragment {
            val fragment = StorageFragment()
            val args = Bundle()
            args.putString("path", path)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EventBus.getDefault().register(this)
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().post(RefreshUpLoadFragmentBean())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            storageAdapter = StorageAdapter(it, viewModel.storageList)
            rv_storage.layoutManager = LinearLayoutManager(it)
            rv_storage.adapter = storageAdapter
            storageAdapter?.addOnItemClickListener(this)
        }

        arguments?.let { it ->
            //?????????????????????
            viewModel.path = it.getString("path")
            val list = viewModel.path?.let { it1 -> Utils.getAllFiles(it1) }
            //????????????????????????????????????(????????????????????????)
            val listNotDirectory = ArrayList<FileBean>()
            if (list != null && list.isNotEmpty()) {
                //??????(??????????????????)
                Collections.sort(list, String.CASE_INSENSITIVE_ORDER)
                for (path in list) {
                    //????????????????????????
                    if (!path.substring(path.lastIndexOf("/") + 1).startsWith(".")) {
                        //???????????????????????????
                        if (File(path).isDirectory) {
                            //??????????????????
                            viewModel.storageList.add(
                                FileBean(
                                    path.substring(path.lastIndexOf("/") + 1),
                                    path,
                                    true,
                                    getFileNum(path)
                                )
                            )
                        } else {
                            //???????????????
                            listNotDirectory.add(
                                FileBean(
                                    path.substring(path.lastIndexOf("/") + 1),
                                    path,
                                    false,
                                    0
                                )
                            )
                        }
                    }
                }
                //??????????????????????????????
                viewModel.storageList.addAll(listNotDirectory)
                storageAdapter?.notifyDataSetChanged()
                //???????????????????????????????????????????????????
                if (viewModel.storageList.size > 0) {
                    tv_without_file.visibility = View.GONE
                } else {
                    tv_without_file.visibility = View.VISIBLE
                }
            } else {
                tv_without_file.visibility = View.VISIBLE
            }
        }
        refreshList()
    }

    override fun onItemClick(position: Int) {
        if (viewModel.storageList[position].isDirectory) {
            //????????????????????????
            storageFragment = newInstance(viewModel.storageList[position].path)
            storageFragment?.let {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    add(R.id.fl_container, it)
                    addToBackStack("")
                    commit()
                }
            }
        } else {
            //???????????????????????????????????????
            activity?.let {
                val localUpdateActivity = it as LocalUpdateActivity
                storageAdapter?.let { adapter ->
                    if (adapter.isSelected(position)) {
                        adapter.setItemChecked(position, false)
                        localUpdateActivity.pathList.remove(viewModel.storageList[position].path)
                        localUpdateActivity.setText()
                    } else {
                        if (localUpdateActivity.pathList.size >= localUpdateActivity.maxNum!!) {
                            //?????????????????????????????????????????????????????????????????????????????????
                            EventBus.getDefault().post(RemoveFileBean(localUpdateActivity.pathList[0]))
                            localUpdateActivity.pathList.removeAt(0)
                        }
                        adapter.setItemChecked(position, true)
                        localUpdateActivity.pathList.add(viewModel.storageList[position].path)
                        localUpdateActivity.setText()
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(refreshUpLoadFragmentBean: RefreshUpLoadFragmentBean) {
        refreshList()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(removeFileBean: RemoveFileBean) {
        removeList(removeFileBean.path)
    }

    /**
     * ???????????????????????????item????????????
     */
    private fun refreshList() {
        val localUpdateActivity = activity as LocalUpdateActivity
        if (localUpdateActivity.pathList.size > 0) {
            for (i in 0 until viewModel.storageList.size) {
                for (j in 0 until localUpdateActivity.pathList.size) {
                    if (localUpdateActivity.pathList[j] equal viewModel.storageList[i].path) {
                        storageAdapter?.setItemChecked(i, true)
                        storageAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    /**
     * ??????item??????
     */
    private fun removeList(path:String){
        for(i in 0 until viewModel.storageList.size){
            if(viewModel.storageList[i].path equal path){
                storageAdapter?.setItemChecked(i, false)
                storageAdapter?.notifyDataSetChanged()
            }
        }
    }

    /**
     * ?????????????????????????????????
     */
    private fun getFileNum(path: String): Int {
        var num = 0
        val list = Utils.getAllFiles(path)
        for (path in list) {
            if (!path.substring(path.lastIndexOf("/") + 1).startsWith(".")) {
                num++
            }
        }
        return num
    }

}