package com.cloud.leasing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cloud.leasing.R
import com.cloud.leasing.bean.HomeDeviceBean
import com.cloud.leasing.bean.Search
import com.cloud.leasing.callback.OnItemChildrenListener
import com.cloud.leasing.callback.OnItemViewClickListener
import com.cloud.leasing.constant.Constant
import com.google.android.material.imageview.ShapeableImageView

class MoreDeviceAdapter(val context: Context, var list: MutableList<Search>) :
    BaseAdapter() {

    fun refreshData(list: MutableList<Search>) {
        this.list = list
        notifyDataSetChanged()
    }

    var onItemViewClickListener: OnItemViewClickListener? = null

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHoler: ViewHoler? = null
        var view: View? = null
        if (convertView == null) {
            viewHoler = ViewHoler()
            view = LayoutInflater.from(context).inflate(R.layout.layout_more_device_item, null)
            viewHoler.device_item_name_tv = view.findViewById(R.id.device_item_name_tv)
            viewHoler.device_item_img = view.findViewById(R.id.device_item_img)
            viewHoler.device_item_type_tv = view.findViewById(R.id.device_item_type_tv)
            viewHoler.device_item_diameter_tv = view.findViewById(R.id.device_item_diameter_tv)
            viewHoler.device_item_follow_tv = view.findViewById(R.id.device_item_follow_tv)
            view.tag = viewHoler
        } else {
            view = convertView
            viewHoler = view.tag as ViewHoler
        }
        viewHoler.device_item_name_tv!!.text =
            list[position].deviceCity + "??" + list[position].deviceBrand
        viewHoler.device_item_type_tv!!.text = list[position].deviceType
        viewHoler.device_item_diameter_tv!!.text = "???????????? " + list[position].outerDiameter
        viewHoler.device_item_img?.let {
            Glide.with(context)
                .load(Constant.BASE_FILE_URL + list[position].filePath)
                .centerCrop()
                .placeholder(R.mipmap.icon_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(it)
        }
        when (list[position].focusStatus) {
            "0" -> {
                viewHoler.device_item_follow_tv?.let {
                    val drawableLeft: Drawable =
                        context.resources.getDrawable(
                            R.mipmap.icon_follow_no
                        )
                    it.setCompoundDrawablesWithIntrinsicBounds(
                        drawableLeft,
                        null, null, null
                    )
                    it.text = "??????"
                    it.compoundDrawablePadding = 10
                }
            }
            "1" -> {
                viewHoler.device_item_follow_tv?.let {
                    val drawableLeft: Drawable =
                        context.resources.getDrawable(
                            R.mipmap.icon_follow_yes
                        )
                    it.setCompoundDrawablesWithIntrinsicBounds(
                        drawableLeft,
                        null, null, null
                    )
                    it.text = "?????????"
                    it.compoundDrawablePadding = 10
                }
            }
        }
        viewHoler.device_item_follow_tv?.let { it ->
            it.setOnClickListener {
                onItemViewClickListener?.onChildren(position, list[position], it)
            }
        }
        return view!!
    }

    class ViewHoler {
        var device_item_name_tv: TextView? = null
        var device_item_img: ShapeableImageView? = null
        var device_item_type_tv: TextView? = null
        var device_item_diameter_tv: TextView? = null
        var device_item_follow_tv: TextView? = null
    }

}