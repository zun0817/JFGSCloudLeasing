package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloud.leasing.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class AddRequireFileAdapter extends CommonAdapter<String> {

    private Context context;

    public AddRequireFileAdapter(Context context, List<String> selectPath, RecyclerView rvImages) {
        super(context, R.layout.layout_add_file_item, selectPath);
        this.context = context;
        selectPath.add("");
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, final int position) {
        TextView add_file_name_tv = viewHolder.getView(R.id.add_file_name_tv);
        TextView add_file_size_tv = viewHolder.getView(R.id.add_file_size_tv);
        ImageView add_file_delete_img = viewHolder.getView(R.id.add_file_delete_img);
        add_file_delete_img.setOnClickListener(new PicturesClickListener(position));
    }

    /**
     * 图片点击事件
     */
    private class PicturesClickListener implements View.OnClickListener {

        int position;

        public PicturesClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.add_picture_delete_img) {
                getDatas().remove(position);
                if (!getDatas().get(getDatas().size() - 1).equals("")) {
                    //列表最后一张不是添加按钮时，加入添加按钮
                    getDatas().add("");
                }
                notifyDataSetChanged();
            }
        }
    }

}