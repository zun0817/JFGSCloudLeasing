package com.cloud.leasing.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.leasing.R;
import com.cloud.leasing.bean.ProductDailyFaultBean;
import com.cloud.leasing.callback.OnDeleteFileListener;
import com.cloud.leasing.util.FileUtilKt;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.List;

public class AddFaultAdapter extends CommonAdapter<ProductDailyFaultBean> {

    private Context context;

    OnDeleteFileListener onDeleteFileListener;

    public AddFaultAdapter(Context context, List<ProductDailyFaultBean> selectPath) {
        super(context, R.layout.layout_fault_item, selectPath);
        this.context = context;
    }

    public void setOnDeleteFileListener(OnDeleteFileListener listener) {
        this.onDeleteFileListener = listener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, ProductDailyFaultBean item, final int position) {
        TextView fault_ringnumber_tv = viewHolder.getView(R.id.fault_ringnumber_tv);
        TextView fault_faultbody_tv = viewHolder.getView(R.id.fault_faultbody_tv);
        ImageView fault_delete_img = viewHolder.getView(R.id.fault_delete_img);
        fault_ringnumber_tv.setText("环号：" + item.getRingNumber());
        fault_faultbody_tv.setText("故障部位：" + item.getFaultBody());
        fault_delete_img.setOnClickListener(v -> {
            getDatas().remove(position);
            notifyDataSetChanged();
            onDeleteFileListener.onDelete(position);
        });
    }
}