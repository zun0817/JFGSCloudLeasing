package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloud.leasing.R;
import com.cloud.leasing.bean.AddMaintenanceBean;
import com.cloud.leasing.bean.ManageFileBean;
import com.cloud.leasing.callback.OnItemChildrenListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class AddMaintenanceAdapter extends CommonAdapter<AddMaintenanceBean> {

    private OnItemChildrenListener onItemChildrenListener;

    public AddMaintenanceAdapter(Context context, List<AddMaintenanceBean> list) {
        super(context, R.layout.layout_add_maintenance_item, list);
    }

    public void setOnItemChildrenListener(OnItemChildrenListener onItemChildrenListener) {
        this.onItemChildrenListener = onItemChildrenListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, AddMaintenanceBean item, final int position) {
        TextView add_maintenance_item_devicename_tv = viewHolder.getView(R.id.add_maintenance_item_devicename_tv);
        TextView add_maintenance_item_devicesystem_tv = viewHolder.getView(R.id.add_maintenance_item_devicesystem_tv);
        TextView add_maintenance_item_devicesite_tv = viewHolder.getView(R.id.add_maintenance_item_devicesite_tv);
        TextView add_maintenance_item_matter_tv = viewHolder.getView(R.id.add_maintenance_item_matter_tv);
        TextView add_maintenance_item_edit_tv = viewHolder.getView(R.id.add_maintenance_item_edit_tv);
        add_maintenance_item_devicename_tv.setText(item.getMtnceName());
        add_maintenance_item_devicesystem_tv.setText(item.getMtnceSystem());
        add_maintenance_item_devicesite_tv.setText(item.getMtncePosition());
        add_maintenance_item_matter_tv.setText(item.getNeedingAttention());
        add_maintenance_item_edit_tv.setOnClickListener(v -> {
            onItemChildrenListener.onChildren(position, item);
        });
    }
}