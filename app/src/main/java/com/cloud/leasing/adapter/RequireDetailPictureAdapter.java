package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloud.leasing.R;
import com.cloud.leasing.bean.DemandFile;
import com.cloud.leasing.bean.RentDeviceFile;
import com.cloud.leasing.constant.Constant;
import com.google.android.material.imageview.ShapeableImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class RequireDetailPictureAdapter extends CommonAdapter<DemandFile> {

    public RequireDetailPictureAdapter(Context context, List<DemandFile> selectPath) {
        super(context, R.layout.layout_detail_picture_item, selectPath);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DemandFile item, final int position) {
        ShapeableImageView detail_picture_img = viewHolder.getView(R.id.detail_picture_img);
        TextView detail_picture_label_tv = viewHolder.getView(R.id.detail_picture_label_tv);
        detail_picture_label_tv.setVisibility(View.INVISIBLE);
        Glide.with(mContext)
                .load(Constant.BASE_FILE_URL + item.getFilePath())
                .centerCrop()
                .placeholder(R.mipmap.icon_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(detail_picture_img);
    }
}