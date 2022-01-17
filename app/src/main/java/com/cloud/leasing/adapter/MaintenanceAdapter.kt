package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.Maintenance

class MaintenanceAdapter(val context: Context, var list: MutableList<Maintenance>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<Maintenance>) {
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_maintenance_item, null)
            viewHoler.maintenance_item_creattime_tv =
                view.findViewById(R.id.maintenance_item_creattime_tv)
            viewHoler.maintenance_item_tv = view.findViewById(R.id.maintenance_item_tv)
            viewHoler.maintenance_item_desc_tv = view.findViewById(R.id.maintenance_item_desc_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.maintenance_item_creattime_tv!!.text = "创建时间 " + list[position].createTime
        viewHoler.maintenance_item_tv!!.text = list[position].abnormalTotal.toString() + "项"
        viewHoler.maintenance_item_desc_tv!!.text = list[position].exceptionDetails
        return view!!
    }

    class ViewHoler {
        var maintenance_item_creattime_tv: TextView? = null
        var maintenance_item_tv: TextView? = null
        var maintenance_item_desc_tv: TextView? = null
    }

}