package com.keihong.imapp.common.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.keihong.imapp.common.mvp.BaseModel;
import com.keihong.imapp.common.mvp.BasePresenter;
import com.keihong.imapp.common.mvp.MVPUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author KeiHong
 * @time 2017/6/17. 2:01
 * @description protected 继承者都可以复写
 * abstract: 子类必须复写
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {

    public Context activity;
    public T mPresenter;
    public E mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在界面未初始化之前调用的初始化窗口
        initWidows();


        if (initArgs(getIntent().getExtras())) {
            setContentView(getContentLayoutId());
            initWidget();
            initLinstener();
            initData();
            //初试化MVP
            initMVP();
        } else {
            finish();
        }

    }

    private void initMVP() {
        //初始化mvp
        activity = this;

        if (MVPUtil.getT(this, 0) == null){
            Log.e("mvp_init","参数为空");
            return;
        }

        mPresenter = MVPUtil.getT(this, 0);
        mModel = MVPUtil.getT(this, 1);

        if (null != mPresenter) {
            mPresenter.mContext = this;
        }

        initPresenter();
    }

    protected void initPresenter() {
        mPresenter.setViewAndModel(this, mModel);
    }

    /**
     * 初始化窗口
     */
    protected void initWidows() {

    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True，错误返回False
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    protected abstract int getContentLayoutId();


    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
        initLinstener();
    }

    protected void initLinstener() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 当点击界面导航返回时，Finish当前界面
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 当按下物理返回键，finish
     * <p>
     * 情况：一个Activity有多个Fragment，Fragment有层次，点返回键是返回上一个Fragment
     * 解决： Activity和Fragment之间要通信
     */
    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        // 如果有直接Return,无则进行最后面的Activity的Finish
                        return;
                    }
                }
            }

        }

        super.onBackPressed();
        finish();
    }
}
