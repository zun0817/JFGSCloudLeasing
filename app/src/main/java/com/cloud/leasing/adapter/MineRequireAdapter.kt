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
import com.cloud.leasing.bean.MineRequire
import com.cloud.leasing.bean.Search
import com.cloud.leasing.callback.OnItemChildrenListener
import com.cloud.leasing.module.home.detail.RequireDetailActivity

class MineRequireAdapter(val context: Context, var list: MutableList<MineRequire>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<MineRequire>){
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_minepublish_item, null)
            viewHoler.minepublish_item_type_tv = view.findViewById(R.id.minepublish_item_type_tv)
            viewHoler.minepublish_item_city_tv = view.findViewById(R.id.minepublish_item_city_tv)
            viewHoler.minepublish_item_length_tv = view.findViewById(R.id.minepublish_item_length_tv)
            viewHoler.minepublish_item_product_tv = view.findViewById(R.id.minepublish_item_product_tv)
            viewHoler.minepublish_item_num_tv = view.findViewById(R.id.minepublish_item_num_tv)
            viewHoler.minepublish_item_geology_tv = view.findViewById(R.id.minepublish_item_geology_tv)
            viewHoler.minepublish_item_time_tv = view.findViewById(R.id.minepublish_item_time_tv)
            viewHoler.minepublish_item_look_tv = view.findViewById(R.id.minepublish_item_look_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.minepublish_item_type_tv!!.text = list[position].deviceType
        viewHoler.minepublish_item_city_tv!!.text = list[position].projectLocation.toString()
        viewHoler.minepublish_item_length_tv!!.text = list[position].projectLength.toString()
        viewHoler.minepublish_item_product_tv!!.text = list[position].deviceBrand
        viewHoler.minepublish_item_num_tv!!.text = list[position].demandNum.toString()
        viewHoler.minepublish_item_geology_tv!!.text = list[position].geologicalInfo
        viewHoler.minepublish_item_time_tv!!.text = list[position].usageTime.split(" ")[0]
        viewHoler.minepublish_item_look_tv?.let {
            it.setOnClickListener {
                RequireDetailActivity.startActivity(context as Activity, list[position].id)
            }
        }
        return view!!
    }

    class ViewHoler {
        var minepublish_item_type_tv: TextView? = null
        var minepublish_item_city_tv: TextView? = null
        var minepublish_item_length_tv: TextView? = null
        var minepublish_item_product_tv: TextView? = null
        var minepublish_item_num_tv: TextView? = null
        var minepublish_item_geology_tv: TextView? = null
        var minepublish_item_time_tv: TextView? = null
        var minepublish_item_look_tv:TextView? = null
    }

}