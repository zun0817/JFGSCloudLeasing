package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.DeviceResumeBean
import com.cloud.leasing.bean.MineRequire
import com.cloud.leasing.bean.Search
import com.cloud.leasing.callback.OnItemChildrenListener
import com.cloud.leasing.module.device.resume.DeviceResumeDetailActivity
import com.cloud.leasing.module.home.detail.RequireDetailActivity

class DeviceResumeAdapter(val context: Context, var list: MutableList<DeviceResumeBean>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<DeviceResumeBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHoler: ViewHoler? = null
        var view: View? = null
        if (convertView == null) {
            viewHoler = ViewHoler()
            view = LayoutInflater.from(context).inflate(R.layout.layout_resume_item, null)
            viewHoler.resume_item_no_tv = view.findViewById(R.id.resume_item_no_tv)
            viewHoler.resume_item_tunnemeters_tv =
                view.findViewById(R.id.resume_item_tunnemeters_tv)
            viewHoler.resume_item_tunnemeterstotal_tv =
                view.findViewById(R.id.resume_item_tunnemeterstotal_tv)
            viewHoler.resume_item_workingdiam_tv =
                view.findViewById(R.id.resume_item_workingdiam_tv)
            viewHoler.resume_item_time_tv = view.findViewById(R.id.resume_item_time_tv)
            viewHoler.resume_item_status_tv = view.findViewById(R.id.resume_item_status_tv)
            viewHoler.resume_item_look_tv = view.findViewById(R.id.resume_item_look_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.resume_item_no_tv!!.text = list[position].deviceNo
        viewHoler.resume_item_tunnemeters_tv!!.text = list[position].tunneMeters + "m"
        viewHoler.resume_item_tunnemeterstotal_tv!!.text = list[position].tunneMeterTotal + "m"
        viewHoler.resume_item_workingdiam_tv!!.text = list[position].workingDiam + "m"
        var starttime = ""
        var exittime = ""
        starttime = if (list[position].startTime == null) {
            "暂无"
        } else {
            list[position].startTime.toString().split(" ")[0]
        }
        exittime = if (list[position].exitTime == null) {
            "暂无"
        } else {
            list[position].exitTime.toString().split(" ")[0]
        }
        viewHoler.resume_item_time_tv!!.text = "$starttime~$exittime"
        when (list[position].deviceResumeStatus) {
            "1" -> {
                viewHoler.resume_item_status_tv!!.text = "存放"
                viewHoler.resume_item_status_tv!!.setBackgroundResource(R.drawable.shape_view_ffaa33_2)
            }
            "2" -> {
                viewHoler.resume_item_status_tv!!.text = "使用"
                viewHoler.resume_item_status_tv!!.setBackgroundResource(R.drawable.shape_view_13ad6b_2)
            }
            "3" -> {
                viewHoler.resume_item_status_tv!!.text = "维修"
                viewHoler.resume_item_status_tv!!.setBackgroundResource(R.drawable.shape_view_bfbfbf_2)
            }
        }
        viewHoler.resume_item_look_tv?.let {
            it.setOnClickListener {
                DeviceResumeDetailActivity.startActivity(
                    context as Activity,
                    list[position].id,
                    list[position].deviceResumeStatus,
                    list[position].deviceNo
                )
            }
        }
        return view!!
    }

    class ViewHoler {
        var resume_item_no_tv: TextView? = null
        var resume_item_tunnemeters_tv: TextView? = null
        var resume_item_tunnemeterstotal_tv: TextView? = null
        var resume_item_workingdiam_tv: TextView? = null
        var resume_item_time_tv: TextView? = null
        var resume_item_status_tv: TextView? = null
        var resume_item_look_tv: TextView? = null
    }

}