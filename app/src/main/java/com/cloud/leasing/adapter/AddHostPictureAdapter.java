package com.cloud.leasing.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cloud.leasing.R;
import com.cloud.leasing.callback.OnAddPicturesListener;
import com.cloud.leasing.callback.OnDeleteFileListener;
import com.giftedcat.picture.lib.photoview.GlideImageLoader;
import com.giftedcat.picture.lib.photoview.style.index.NumberIndexIndicator;
import com.giftedcat.picture.lib.photoview.style.progress.ProgressBarIndicator;
import com.giftedcat.picture.lib.photoview.transfer.TransferConfig;
import com.giftedcat.picture.lib.photoview.transfer.Transferee;
import com.google.android.material.imageview.ShapeableImageView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class AddHostPictureAdapter extends CommonAdapter<String> {

    private Context context;

    OnAddPicturesListener listener;

    OnDeleteFileListener onDeleteFileListener;

    protected Transferee transferee;

    protected TransferConfig config;

    public AddHostPictureAdapter(Context context, List<String> selectPath, RecyclerView rvImages) {
        super(context, R.layout.layout_add_picture_item, selectPath);
        this.context = context;
        selectPath.add("");
        initTransfer(rvImages);
    }

    /**
     * 设置最大图片数量
     */
    public void setMaxSize(int maxNum) {
        config.setMax(maxNum);
    }

    /**
     * 设置点击添加按钮的监听
     */
    public void setOnAddPicturesListener(OnAddPicturesListener listener) {
        this.listener = listener;
    }

    public void setOnDeleteFileListener(OnDeleteFileListener listener) {
        this.onDeleteFileListener = listener;
    }

    /**
     * 初始化大图查看控件
     */
    private void initTransfer(RecyclerView rvImages) {
        transferee = Transferee.getDefault(context);
        config = TransferConfig.build()
                .setSourceImageList(getDatas())
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(context.getApplicationContext()))
                .setJustLoadHitImage(true)
                .bindRecyclerView(rvImages, R.id.add_picture_img);
    }


    @Override
    protected void convert(ViewHolder viewHolder, String item, final int position) {
        ImageView add_picture_delete_img = viewHolder.getView(R.id.add_picture_delete_img);
        ShapeableImageView add_picture_img = viewHolder.getView(R.id.add_picture_img);
        FrameLayout add_picture_fl = viewHolder.getView(R.id.add_picture_fl);
        ImageView add_picture = viewHolder.getView(R.id.add_picture);
        if (item.equals("")) {
            //item为添加按钮
            add_picture_fl.setVisibility(View.GONE);
            add_picture.setVisibility(View.VISIBLE);
        } else {
            //item为普通图片
            add_picture_fl.setVisibility(View.VISIBLE);
            add_picture.setVisibility(View.GONE);
        }
        Glide.with(mContext).load(item).into(add_picture_img);
        add_picture_img.setOnClickListener(new PicturesClickListener(position));
        add_picture.setOnClickListener(new PicturesClickListener(position));
        add_picture_delete_img.setOnClickListener(new PicturesClickListener(position));
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
            switch (view.getId()) {
                case R.id.add_picture_img:
                    //点击图片
                    config.setNowThumbnailIndex(position);
                    config.setSourceImageList(getDatas());
                    transferee.apply(config).show();
                    break;
                case R.id.add_picture:
                    //点击添加按钮
                    if (listener != null)
                        listener.onAdd();
                    break;
                case R.id.add_picture_delete_img:
                    getDatas().remove(position);
//                    if (!getDatas().get(getDatas().size() - 1).equals("")) {
//                        //列表最后一张不是添加按钮时，加入添加按钮
//                        getDatas().add("");
//                    }
                    notifyDataSetChanged();
                    onDeleteFileListener.onDelete(position);
                    break;
            }
        }
    }

}