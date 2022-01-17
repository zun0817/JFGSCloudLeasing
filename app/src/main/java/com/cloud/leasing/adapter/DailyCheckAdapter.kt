package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.StoreDaily

class DailyCheckAdapter(val context: Context, var list: MutableList<StoreDaily>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<StoreDaily>) {
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_daily_check_item, null)
            viewHoler.daily_check_item_creattime_tv =
                view.findViewById(R.id.daily_check_item_creattime_tv)
            viewHoler.daily_check_status_item_tv = view.findViewById(R.id.daily_check_status_item_tv)
            viewHoler.daily_check_item_desc_tv = view.findViewById(R.id.daily_check_item_desc_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.daily_check_item_creattime_tv!!.text = "创建时间 " + list[position].createTime
        when(list[position].deviceStatus){
            "1" -> viewHoler.daily_check_status_item_tv!!.text = "正常"
            "2" -> viewHoler.daily_check_status_item_tv!!.text = "异常"
        }
        viewHoler.daily_check_item_desc_tv!!.text = list[position].exceptionDetails
        return view!!
    }

    class ViewHoler {
        var daily_check_item_creattime_tv: TextView? = null
        var daily_check_status_item_tv: TextView? = null
        var daily_check_item_desc_tv: TextView? = null
    }

}