package com.keihong.imapp.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keihong.imapp.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author KeiHong
 * @time 2017/7/6. 22:33
 * @description
 */

@SuppressWarnings("ALL")
public abstract class RecyclerAdapter<Data> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnLongClickListener, View.OnClickListener, AdapterCallback<Data> {

    private final List<Data> mDataList;
    private AdapterListener<Data> mListener;

    /**
     * 构造函数模块
     *
     * @param dataList
     * @param listener
     */
    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    /**
     * 没有数据，只有监听
     *
     * @param listener
     */
    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    /**
     * 没有数据，没有监听
     */
    public RecyclerAdapter() {
        this(null);
    }

    /**
     * 覆写默认的布局类型返回
     *
     * @param position 坐标
     * @return 类型，复写后，返回的viewType都是xml（列表子项布局文件）id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * 得到布局类的类型
     *
     * @param position 坐标
     * @param data     当前数据
     * @return XML文件ID，用于创建ViewHolder
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 创建一个ViewHolder
     * <p>
     * 根据不同的viewType来生成ViewHolder
     *
     * @param parent   RecyclerView
     * @param viewType 界面类型 -- item xml id（约定为列表项的布局id）
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        //得到LayoutInflater用于将XML初始化为View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //把XML id为 viewType 的布局文件初始化root View
        View root = inflater.inflate(viewType, parent, false);
        //通过子类必须实现的方法，得到一个ViewHolder
        ViewHolder<Data> viewHolder = onCreateViewHolder(root, viewType);

        //设置View的Tag为ViewHolder，进行双向绑定
        root.setTag(R.id.tag_recycler_holder, viewHolder);

        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);


        //进行界面注解绑定
        viewHolder.mUnbinder = ButterKnife.bind(viewHolder, root);
        //绑定Callback，用于界面刷新操作
        viewHolder.mDataAdapterCallback = this;

        return viewHolder;
    }

    /**
     * 当得到一个新的ViewHolder
     *
     * @param root     根布局
     * @param viewType 布局类型 == xml id
     * @return ViewHolder
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 绑定数据到一个Holder上
     *
     * @param holder   ViewHolder
     * @param position 坐标
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //得到需要绑定的数据
        Data data = mDataList.get(position);
        //触发Holder的绑定方法
        holder.bind(data);

    }


    /**
     * 得到当前集合的数据量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 插入一条数据并通知插入
     *
     * @param data Data
     */
    public void add(Data data) {
        mDataList.add(data);
//        notifyDataSetChanged();
        //只需更新插入的位置
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据并通知这段集合更新
     *
     * @param datas Data
     */
    public final void add(Data... datas) {
        if (datas != null && datas.length > 0) {
            int startPos = mDataList.size();
            Collections.addAll(mDataList, datas);
            notifyItemRangeInserted(startPos, datas.length);
        }
    }

    /**
     * 插入一堆数据并通知这段集合更新
     *
     * @param datas Data
     */
    public void add(Collection<Data> datas) {
        if (datas != null && datas.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(datas);
            notifyItemRangeInserted(startPos, datas.size());
        }
    }

    /**
     * 删除全部数据
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换数据集合
     *
     * @param datas
     */
    public void replace(Collection<Data> datas) {
        mDataList.clear();

        if (datas == null || datas.size() == 0) return;

        mDataList.addAll(datas);
        notifyDataSetChanged();

    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        //得到当前ViewHolder的坐标
        int pos = holder.getAdapterPosition();
        if (pos >= 0) {
            //进行数据的移除与更新
            mDataList.remove(pos);
            mDataList.add(pos, data);
            //通知这个坐标下的数据有更新
            notifyItemChanged(pos);
        }
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }

    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            //得到ViewHolder当前对应的适配器中的坐标
            int pos = viewHolder.getAdapterPosition();
            //回调
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器点击监听
     *
     * @param listener
     */
    public void setListener(AdapterListener<Data> listener) {
        this.mListener = listener;

    }

    /**
     * 自定义Cell点击事件监听器
     * 返回数据给界面进行操作
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        //当Cell点击时触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        //当Cell长按时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    /**
     * 自定义ViewHolder
     *
     * @param <Data> 范型类型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder mUnbinder;
        private AdapterCallback<Data> mDataAdapterCallback;
        protected Data mData;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候，回调
         * 必须覆写
         *
         * @param data
         */
        protected abstract void onBind(Data data);

        /**
         * Holder自己对自己对应的Data进行更新操作
         *
         * @param data Data数据
         */
        public void updateData(Data data) {
            if (this.mDataAdapterCallback != null) {
                this.mDataAdapterCallback.update(data, this);
            }

        }
    }


    /**
     * 对回调接口做一次实现，外部使用时，就可以自由选择覆写的方法，而不用都实现
     *
     * @param <Data>
     */
    public static abstract class AdapterListenerImpl<Data> implements AdapterListener<Data> {

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }
}
