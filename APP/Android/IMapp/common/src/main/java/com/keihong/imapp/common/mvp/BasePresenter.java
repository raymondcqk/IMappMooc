package com.keihong.imapp.common.mvp;

import android.content.Context;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

//T代表View,E代表Model
public abstract class BasePresenter<T, E> {

    public Context mContext;
    public T mView;
    public E mModel;

    public void setViewAndModel(T view, E model) {
        this.mView = view;
        this.mModel = model;
    }

    public  void onStart(){

    }

    public  void onDestroy(){

    }
}
