package com.cloud.leasing.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.leasing.R;
import com.cloud.leasing.bean.ManageFileBean;
import com.cloud.leasing.bean.RentDeviceFile;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class ManageDataFileAdapter extends CommonAdapter<ManageFileBean> {

    public ManageDataFileAdapter(Context context, List<ManageFileBean> selectPath) {
        super(context, R.layout.layout_detail_file_item, selectPath);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ManageFileBean item, final int position) {
        TextView detail_file_name_tv = viewHolder.getView(R.id.detail_file_name_tv);
        TextView detail_file_size_tv = viewHolder.getView(R.id.detail_file_size_tv);
        ImageView detail_file_cache_img = viewHolder.getView(R.id.detail_file_cache_img);
        detail_file_name_tv.setText(item.getFileName());
        detail_file_size_tv.setText(item.getCreateTime());
    }
}