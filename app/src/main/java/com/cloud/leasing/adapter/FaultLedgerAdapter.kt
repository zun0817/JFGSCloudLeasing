package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cloud.leasing.R
import com.cloud.leasing.bean.FaultRecord
import com.cloud.leasing.bean.RecordBean

class FaultLedgerAdapter(val context: Context, var list: MutableList<FaultRecord>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<FaultRecord>){
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_fault_ledger_item, null)
            viewHoler.fault_ledger_item_creattime_tv = view.findViewById(R.id.fault_ledger_item_creattime_tv)
            viewHoler.fault_ledger_item_site_tv = view.findViewById(R.id.fault_ledger_item_site_tv)
            viewHoler.fault_ledger_item_ring_tv = view.findViewById(R.id.fault_ledger_item_ring_tv)
            viewHoler.fault_ledger_item_desc_tv = view.findViewById(R.id.fault_ledger_item_desc_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.fault_ledger_item_creattime_tv!!.text = "创建时间 " + list[position].createTime
        viewHoler.fault_ledger_item_ring_tv!!.text = list[position].ringNumber
        viewHoler.fault_ledger_item_site_tv!!.text = list[position].faultBody
        viewHoler.fault_ledger_item_desc_tv!!.text = list[position].faultDesc
        return view!!
    }

    class ViewHoler {
        var fault_ledger_item_creattime_tv: TextView? = null
        var fault_ledger_item_site_tv: TextView? = null
        var fault_ledger_item_ring_tv: TextView? = null
        var fault_ledger_item_desc_tv: TextView? = null
    }

}