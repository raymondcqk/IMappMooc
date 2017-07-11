package com.keihong.imapp.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author KeiHong
 * @time 2017/6/17. 2:03
 * @description
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment {

    //复用根布局view
    protected View mRoot;
    protected Unbinder mRootUnBinder;

    /**
     * 初始化参数
     *
     *
     * 当Fragment被添加到Activity的时候执行
     * 比onCreateView更优先
     *
     * Context == BaseActivity
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        initArgs(getArguments());
        /**
         * 参数
         *  - Activity中是 getIntent().getExtras()
         *  - Fragment是 getArguments()
         *
         * 对比记忆
         */
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**
         * Root的调度：在inflate时，不添加到container，但在return之后
         * 在Fragment内部的调用中，调度之后，会将root添加到container（其父布局）
         *
         * 如果Fragment被回收，重新初始化时，可能root没被回收，则root的父布局也有数据
         * 移除掉，重新进入调度
         *
         */

        if (mRoot==null){
            int layId = getContentLayoutId();
            // 初始化当前的跟布局，但是不在创建时就添加到container里边
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        }else {
            if (mRoot.getParent()!=null){
                //把当前Root从其父控件中移除
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;


    }

    /**
     * 当界面初始化完成之后执行 onCreateView之后（view都实例化完成）
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 当View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     *
     * 因为不可控，所以不返回boolean参数，和Activity中不同，没有finish
     */
    protected void initArgs(Bundle bundle) {

    }


    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this,root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按键触发时调用
     *      (Fragment中没有BackPressed，自己写一个)
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     *
     * #拦截机制#
     */
    public boolean onBackPressed() {
        return false;
    }
}
