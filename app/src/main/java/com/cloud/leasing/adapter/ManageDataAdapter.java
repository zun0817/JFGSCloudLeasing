package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cloud.leasing.R;
import com.cloud.leasing.bean.ManageDataBean;
import com.cloud.leasing.callback.OnChildItemClickListener;
import com.cloud.leasing.util.ViewTouchUtil;

import java.util.ArrayList;

public class ManageDataAdapter extends BaseExpandableListAdapter {

    private Context context;

    private ArrayList<ManageDataBean> list;

    private OnChildItemClickListener onChildItemClickListener;

    public void setOnChildItemClickListener(OnChildItemClickListener onChildItemClickListener) {
        this.onChildItemClickListener = onChildItemClickListener;
    }

    public ManageDataAdapter(Context context, ArrayList<ManageDataBean> list) {
        this.list = list;
        this.context = context;
    }

    public void refreshData(ArrayList<ManageDataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChildrenList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChildrenList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_manage_data_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.manage_data_name_tv = (TextView) convertView.findViewById(R.id.manage_data_name_tv);
            groupViewHolder.manage_data_expand_image = (ImageView) convertView.findViewById(R.id.manage_data_expand_image);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.manage_data_name_tv.setText(list.get(groupPosition).getManageName());
        if (isExpanded) {
            groupViewHolder.manage_data_expand_image.setImageResource(R.mipmap.icon_expand_yes);
        } else {
            groupViewHolder.manage_data_expand_image.setImageResource(R.mipmap.icon_expand_no);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_manage_data_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.manage_data_child_name_tv = (TextView) convertView.findViewById(R.id.manage_data_child_name_tv);
            childViewHolder.manage_data_child_edit_tv = (TextView) convertView.findViewById(R.id.manage_data_child_edit_tv);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.manage_data_child_name_tv.setText(list.get(groupPosition).getChildrenList().get(childPosition).getManageName());
        childViewHolder.manage_data_child_edit_tv.setOnClickListener(v -> onChildItemClickListener.onChildClick(groupPosition, childPosition));
        ViewTouchUtil.INSTANCE.expandViewTouchDelegate(childViewHolder.manage_data_child_edit_tv);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        TextView manage_data_name_tv;
        ImageView manage_data_expand_image;
    }

    static class ChildViewHolder {
        TextView manage_data_child_name_tv;
        TextView manage_data_child_edit_tv;
    }
}
