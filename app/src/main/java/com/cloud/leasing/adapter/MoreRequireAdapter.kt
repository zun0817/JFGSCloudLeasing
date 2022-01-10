package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.HomeRequireBean

class MoreRequireAdapter(val context: Context, var list: MutableList<HomeRequireBean>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<HomeRequireBean>){
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_more_require_item, null)
            viewHoler.require_item_type_tv = view.findViewById(R.id.require_item_type_tv)
            viewHoler.require_item_city_tv = view.findViewById(R.id.require_item_city_tv)
            viewHoler.require_item_length_tv = view.findViewById(R.id.require_item_length_tv)
            viewHoler.require_item_product_tv = view.findViewById(R.id.require_item_product_tv)
            viewHoler.require_item_num_tv = view.findViewById(R.id.require_item_num_tv)
            viewHoler.require_item_geology_tv = view.findViewById(R.id.require_item_geology_tv)
            viewHoler.require_item_time_tv = view.findViewById(R.id.require_item_time_tv)
            viewHoler.require_item_look_tv = view.findViewById(R.id.require_item_look_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.require_item_type_tv!!.text = list[position].deviceTypeName
        viewHoler.require_item_city_tv!!.text = list[position].demandCity
        viewHoler.require_item_length_tv!!.text = list[position].projectLength
        viewHoler.require_item_product_tv!!.text = list[position].deviceBrandName
        viewHoler.require_item_num_tv!!.text = list[position].demandNum
        viewHoler.require_item_geology_tv!!.text = list[position].geologicalInfo
        viewHoler.require_item_time_tv!!.text = list[position].usageTime
        viewHoler.require_item_look_tv?.let {
            it.setOnClickListener {

            }
        }
        return view!!
    }

    class ViewHoler {
        var require_item_type_tv: TextView? = null
        var require_item_city_tv: TextView? = null
        var require_item_length_tv: TextView? = null
        var require_item_product_tv: TextView? = null
        var require_item_num_tv: TextView? = null
        var require_item_geology_tv: TextView? = null
        var require_item_time_tv: TextView? = null
        var require_item_look_tv:TextView? = null
    }

}