package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.Search
import com.cloud.leasing.callback.OnItemChildrenListener

class SearchRequireAdapter(val context: Context, var list: MutableList<Search>) :
    BaseAdapter() {

    var onItemChildrenListener: OnItemChildrenListener? = null

    fun refreshData(list: MutableList<Search>){
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
        viewHoler.require_item_type_tv!!.text = list[position].deviceType
        viewHoler.require_item_city_tv!!.text = list[position].demandCity.toString()
        viewHoler.require_item_length_tv!!.text = list[position].projectLength.toString()
        viewHoler.require_item_product_tv!!.text = list[position].deviceBrand
        viewHoler.require_item_num_tv!!.text = list[position].demandNum.toString()
        viewHoler.require_item_geology_tv!!.text = list[position].geologicalInfo.toString()
        viewHoler.require_item_time_tv!!.text = list[position].usageTime.toString().split(" ")[0]
        viewHoler.require_item_look_tv?.let {
            it.setOnClickListener {
                onItemChildrenListener?.onChildren(position, list[position])
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