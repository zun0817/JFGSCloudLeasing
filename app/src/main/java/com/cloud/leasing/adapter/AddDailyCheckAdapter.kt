package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.CheckDailyItemBean
import com.cloud.leasing.bean.StoreDaily

class AddDailyCheckAdapter(val context: Context, var list: MutableList<CheckDailyItemBean>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<CheckDailyItemBean>) {
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_store_daily_item, null)
            viewHoler.store_daily_item_name_tv = view.findViewById(R.id.store_daily_item_name_tv)
            viewHoler.store_daily_item_status_tv =
                view.findViewById(R.id.store_daily_item_status_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.store_daily_item_name_tv!!.text = "检查项: " + list[position].checkName
        when (list[position].deviceStatus) {
            1 -> {
                viewHoler.store_daily_item_status_tv!!.text = "正常"
                viewHoler.store_daily_item_status_tv!!.setTextColor(context.resources.getColor(R.color.color_17191C))
            }
            2 -> {
                viewHoler.store_daily_item_status_tv!!.text = "异常"
                viewHoler.store_daily_item_status_tv!!.setTextColor(context.resources.getColor(R.color.color_FF6A6A))
            }
        }
        return view!!
    }

    class ViewHoler {
        var store_daily_item_name_tv: TextView? = null
        var store_daily_item_status_tv: TextView? = null
    }

}