package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.R
import com.cloud.leasing.bean.MineDevice
import com.cloud.leasing.constant.Constant
import com.google.android.material.imageview.ShapeableImageView

class MineDeviceAdapter(val context: Context, var list: MutableList<MineDevice>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<MineDevice>) {
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
            view = LayoutInflater.from(context).inflate(R.layout.layout_minedevice_item, null)
            viewHoler.minedevice_item_name_tv = view.findViewById(R.id.minedevice_item_name_tv)
            viewHoler.minedevice_item_img = view.findViewById(R.id.minedevice_item_img)
            viewHoler.minedevice_item_type_tv = view.findViewById(R.id.minedevice_item_type_tv)
            viewHoler.minedevice_item_diameter_tv = view.findViewById(R.id.minedevice_item_diameter_tv)
            viewHoler.minedevice_select_img = view.findViewById(R.id.minedevice_select_img)
            viewHoler.minedevice_item_label_tv = view.findViewById(R.id.minedevice_item_label_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.minedevice_item_name_tv!!.text =
            list[position].drivingPosition + "·" + list[position].deviceBrand
        viewHoler.minedevice_item_type_tv!!.text = list[position].deviceType
        viewHoler.minedevice_item_diameter_tv!!.text = "管片外径 " + list[position].cutterDiam
        viewHoler.minedevice_item_img?.let {
            Glide.with(context)
                .load(Constant.BASE_FILE_URL + list[position].deviceMainFileUrl)
                .centerCrop()
                .placeholder(R.mipmap.icon_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it)
        }
        when (list[position].matchStatus) {
            "1" -> {
                viewHoler.minedevice_item_label_tv!!.text = "已匹配"
                viewHoler.minedevice_item_label_tv!!.setBackgroundResource(R.drawable.shape_want_label_0e64bc)
            }
            "2" -> {
                viewHoler.minedevice_item_label_tv!!.text = "未匹配"
                viewHoler.minedevice_item_label_tv!!.setBackgroundResource(R.drawable.shape_want_label_ffaa33)
                when (list[position].deviceStatus) {
                    "0" -> {
                        viewHoler.minedevice_item_label_tv!!.text = "待审核"
                        viewHoler.minedevice_item_label_tv!!.setBackgroundResource(R.drawable.shape_want_label_13ad6b)
                    }
                    "1" -> {
                        viewHoler.minedevice_item_label_tv!!.text = "未通过"
                        viewHoler.minedevice_item_label_tv!!.setBackgroundResource(R.drawable.shape_want_label_bfbfbf)
                    }
                }
            }
        }
        return view!!
    }

    class ViewHoler {
        var minedevice_item_name_tv: TextView? = null
        var minedevice_item_img: ShapeableImageView? = null
        var minedevice_item_type_tv: TextView? = null
        var minedevice_item_diameter_tv: TextView? = null
        var minedevice_select_img: ImageView? = null
        var minedevice_item_label_tv: TextView? = null
    }

}