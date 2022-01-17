package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.RepairDaily

class FaultDailyAdapter(val context: Context, var list: MutableList<RepairDaily>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<RepairDaily>){
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_fault_daily_item, null)
            viewHoler.fault_daily_item_creattime_tv = view.findViewById(R.id.fault_daily_item_creattime_tv)
            viewHoler.fault_daily_item_startdate_tv = view.findViewById(R.id.fault_daily_item_startdate_tv)
            viewHoler.fault_daily_item_progress_tv = view.findViewById(R.id.fault_daily_item_progress_tv)
            viewHoler.fault_daily_item_plandate_tv = view.findViewById(R.id.fault_daily_item_plandate_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.fault_daily_item_creattime_tv!!.text = "创建时间 " + list[position].dateTime
        viewHoler.fault_daily_item_startdate_tv!!.text = list[position].startTime
        viewHoler.fault_daily_item_progress_tv!!.text = list[position].ratio
        viewHoler.fault_daily_item_plandate_tv!!.text = list[position].planTime
        return view!!
    }

    class ViewHoler {
        var fault_daily_item_creattime_tv: TextView? = null
        var fault_daily_item_startdate_tv: TextView? = null
        var fault_daily_item_progress_tv: TextView? = null
        var fault_daily_item_plandate_tv: TextView? = null
    }

}