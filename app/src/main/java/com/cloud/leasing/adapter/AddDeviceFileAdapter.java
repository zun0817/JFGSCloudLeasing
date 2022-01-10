package com.cloud.leasing.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.leasing.R;
import com.cloud.leasing.callback.OnDeleteFileListener;
import com.cloud.leasing.util.FileUtilKt;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.List;

public class AddDeviceFileAdapter extends CommonAdapter<String> {

    private Context context;

    OnDeleteFileListener onDeleteFileListener;

    public AddDeviceFileAdapter(Context context, List<String> selectPath) {
        super(context, R.layout.layout_add_file_item, selectPath);
        this.context = context;
    }

    public void setOnDeleteFileListener(OnDeleteFileListener listener) {
        this.onDeleteFileListener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, final int position) {
        TextView add_file_name_tv = viewHolder.getView(R.id.add_file_name_tv);
        TextView add_file_size_tv = viewHolder.getView(R.id.add_file_size_tv);
        add_file_name_tv.setText(new File(item).getName());
        try {
            long size = FileUtilKt.getFileSize(new File(item));
            add_file_size_tv.setText(FileUtilKt.formatFileSize(size));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView add_file_delete_img = viewHolder.getView(R.id.add_file_delete_img);
        add_file_delete_img.setOnClickListener(v -> {
            getDatas().remove(position);
            notifyDataSetChanged();
            onDeleteFileListener.onDelete(position);
        });
    }
}