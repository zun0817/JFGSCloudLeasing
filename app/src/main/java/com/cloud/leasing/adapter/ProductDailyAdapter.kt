package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.RecordBean

class ProductDailyAdapter(val context: Context, var list: MutableList<RecordBean>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<RecordBean>){
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_product_daily_item, null)
            viewHoler.product_daily_item_creattime_tv = view.findViewById(R.id.product_daily_item_creattime_tv)
            viewHoler.product_daily_item_length_tv = view.findViewById(R.id.product_daily_item_length_tv)
            viewHoler.product_daily_item_product_tv = view.findViewById(R.id.product_daily_item_product_tv)
            viewHoler.product_daily_item_rings_tv = view.findViewById(R.id.product_daily_item_rings_tv)
            viewHoler.product_daily_item_totalrings_tv = view.findViewById(R.id.product_daily_item_totalrings_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.product_daily_item_creattime_tv!!.text = "创建时间 " + list[position].createTime
        viewHoler.product_daily_item_length_tv!!.text = list[position].tunneMeters
        viewHoler.product_daily_item_product_tv!!.text = list[position].progressRatio.toString()
        viewHoler.product_daily_item_rings_tv!!.text = list[position].dailyRings.toString()
        viewHoler.product_daily_item_totalrings_tv!!.text = list[position].cumRings.toString()
        return view!!
    }

    class ViewHoler {
        var product_daily_item_creattime_tv: TextView? = null
        var product_daily_item_length_tv: TextView? = null
        var product_daily_item_product_tv: TextView? = null
        var product_daily_item_rings_tv: TextView? = null
        var product_daily_item_totalrings_tv: TextView? = null
    }

}