package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.leasing.R;
import com.cloud.leasing.bean.DeviceFlie;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class ResumeDetailFileAdapter extends CommonAdapter<DeviceFlie> {

    public ResumeDetailFileAdapter(Context context, List<DeviceFlie> selectPath) {
        super(context, R.layout.layout_detail_file_item, selectPath);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DeviceFlie item, final int position) {
        TextView detail_file_name_tv = viewHolder.getView(R.id.detail_file_name_tv);
        TextView detail_file_size_tv = viewHolder.getView(R.id.detail_file_size_tv);
        ImageView detail_file_cache_img = viewHolder.getView(R.id.detail_file_cache_img);
        if (item.getFilePath().contains(".")) {
            String[] fileSuffix = item.getFilePath().split("\\.");
        }
        detail_file_name_tv.setText(item.getFileName());
        detail_file_size_tv.setVisibility(View.GONE);
        detail_file_cache_img.setOnClickListener(v -> {
            mOnItemClickListener.onItemClick(v, viewHolder, position);
        });
    }
}