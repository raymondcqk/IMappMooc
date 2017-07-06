package com.keihong.imapp.common.widget.recycler;

/**
 * @author KeiHong
 * @time 2017/7/6. 22:32
 * @description
 */

public interface AdapterCallback<Data> {

    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);

}
