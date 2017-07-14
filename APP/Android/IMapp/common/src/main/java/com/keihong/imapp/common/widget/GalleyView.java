package com.keihong.imapp.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.keihong.imapp.common.R;
import com.keihong.imapp.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GalleyView extends RecyclerView {

    private static final int LOADER_ID = 0x100;
    private static final int MAX_IMAGE_SIZE = 3;

    private LoaderCallback mLoaderCallback = new LoaderCallback();
    private Adapter mAdapter = new Adapter();

    private List<Image> mSelectedImages = new LinkedList<>();

    private SelectChangedListener mSelectChangedListener;

    public GalleyView(Context context) {
        super(context);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {
                //Cell点击操作，如果说我们的点击时允许的（未达到最大数量），那么更新对应Cell的状态
                //然后更新界面，反之，若不能点击，则不刷新界面
                if (onItemSelectClick(image)) {
                    holder.updateData(image);
                }
            }
        });
    }

    /**
     * 初始化方法
     *
     * @param loaderManager Loader管理器
     * @return 任何一个Loader_id，可用于销毁loader
     */
    public int setup(LoaderManager loaderManager, SelectChangedListener listener) {
        this.mSelectChangedListener = listener;
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        return LOADER_ID;
    }

    /**
     * 得到选中的图片的全部地址
     *
     * @return 返回一个字符串数组，元素为图片path
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedImages.size()];
        int index = 0;
        for (Image image : mSelectedImages) {
            paths[index++] = image.path;
        }
        return paths;
    }

    /**
     * 清空选中的图片
     */
    public void clear() {
        for (Image image : mSelectedImages) {
            //先重置图片选择状态，Adapter根据data（image）来刷新界面
            image.isSelect = false;
        }
        mSelectedImages.clear();
        //通知数据有更新
        mAdapter.notifyDataSetChanged();
    }

    /**
     * ContentProvider相关
     * 用于实际数据加载的Loader Callback
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        private final Uri IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        private final String[] IMAGE_PROJECTIONS = {
                MediaStore.Images.Media._ID, //图片id
                MediaStore.Images.Media.DATA,//图片路径
                MediaStore.Images.Media.DATE_ADDED//图片创建时间
        };


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ID) {
                return new CursorLoader(
                        getContext(),
                        IMAGE_URI,
                        IMAGE_PROJECTIONS,
                        null, null,
                        IMAGE_PROJECTIONS[2] + " DESC"); //根据创建时间倒序
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data == null) return;
            List<Image> images = new ArrayList<>();
            int count = data.getCount();
            if (count > 0) {
                data.moveToFirst();

                int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTIONS[0]);
                int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTIONS[1]);
                int indexDateTime = data.getColumnIndexOrThrow(IMAGE_PROJECTIONS[2]);

                do {
                    int id = data.getInt(indexId);
                    String path = data.getString(indexPath);
                    long dateTime = data.getLong(indexDateTime);

                    File file = new File(path);
                    if (file.exists()) {
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = dateTime;
                        images.add(image);
                    }

                } while (data.moveToNext());
                mAdapter.replace(images);
            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.replace(null);
        }
    }

    /**
     * Cell具体点击逻辑
     * 返回是否要刷新界面
     *
     * @param image Image
     * @return true 返回true，代表我进行了数据更改，则刷新界面，否则不刷新界面，提示数量满
     */
    private boolean onItemSelectClick(Image image) {
        boolean notifyRefresh;

        //若已经选中，取消选择
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
            image.isSelect = false;
            notifyRefresh = true;
        } else {
            //若为未选中，先查看是否满
            if (mSelectedImages.size() >= MAX_IMAGE_SIZE) {
                // TODO: 2017/7/14 弹出吐司告诉用户数量已满
                String str = getResources().getString(R.string.label_galley_select_max_size);
                str = String.format(str,MAX_IMAGE_SIZE);
                Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            } else {
                //若未满，则添加
                mSelectedImages.add(image);
                image.isSelect = true;
                notifyRefresh = true;
            }
        }

        if (notifyRefresh) {
            notifySelectChanged();
        }
        return true;
    }

    private void notifySelectChanged() {
        mAdapter.notifyDataSetChanged();
        //得到监听者，并判断是否为空，然后进行选中图片的数量回调
        SelectChangedListener listener = mSelectChangedListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectedImages.size());
        }

    }

    /**
     * 内部数据结构
     */
    private static class Image {
        int id;//数据的id
        String path;//图片路径
        long date;//图片创建日期时间
        boolean isSelect;//是否被选中

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Image image = (Image) o;

            return path != null ? path.equals(image.path) : image.path == null;

        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }
    }

    private class Adapter extends RecyclerAdapter<Image> {

        @Override
        public void update(Image image, ViewHolder<Image> holder) {

        }

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_galley;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(android.view.View root, int viewType) {
            return new GalleyView.ViewHolder(root);
        }
    }


    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {


        private ImageView mPic;
        private View mShade;
        private CheckBox mSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            mPic = (ImageView) itemView.findViewById(R.id.im_image);
            mShade = itemView.findViewById(R.id.view_shade);
            mSelected = (CheckBox) itemView.findViewById(R.id.cb_select);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(R.color.grey_100)
                    .into(mPic);
            mShade.setVisibility(image.isSelect?VISIBLE:INVISIBLE);
            mSelected.setChecked(image.isSelect);
            mSelected.setVisibility(VISIBLE);

        }
    }

    public interface SelectChangedListener {
        void onSelectedCountChanged(int count);
    }


}
