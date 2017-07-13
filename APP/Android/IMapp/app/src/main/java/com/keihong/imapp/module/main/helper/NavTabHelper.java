package com.keihong.imapp.module.main.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

import com.keihong.imapp.common.app.BaseFragment;

/**
 * @author KeiHong
 * @time 2017/7/13. 16:24
 * @description 解决对Fragment的调度与重用问题，
 * 达到最优的Fragment切换
 */

public class NavTabHelper<T> {

    //Tab集合
    private final SparseArray<Tab<T>> mTabs = new SparseArray<>();

    //用于初始化的必须参数
    private Context mContext;
    private int mContainerId;
    private FragmentManager mFragmentManager;
    private OnTabChangedListener<T> mListener;

    //当前的Tab
    private Tab<T> mCurrentTab;


    public NavTabHelper(Context context, int containerId,
                        FragmentManager fragmentManager,
                        OnTabChangedListener<T> listener) {
        mContext = context;
        mContainerId = containerId;
        mFragmentManager = fragmentManager;
        mListener = listener;
    }


    /**
     * 添加Tab
     *
     * @param menuId tab对应菜单Id
     * @param tab    Tab
     * @return
     */
    public NavTabHelper<T> add(int menuId, Tab<T> tab) {
        mTabs.put(menuId, tab);
        return this;
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId
     * @return
     */
    public boolean performClickMenu(int menuId) {
        Tab<T> tab = mTabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * 进行真实的Tab选择操作
     *
     * @param tab Tab
     */
    private void doSelect(Tab<T> tab) {

        Tab<T> oldTab = null;

        //判断是否重复点击
        if (tab != null) {
            oldTab = mCurrentTab;
            if (oldTab == tab) {
                //如果当前的tab和新的tab相同，代表是重新点击
                doTabReselected(tab);
                return;
            }
        }

        //是另一个tab的情况
        //要开始切换了，新的tab作为当前的tab，当前的tab已经被记录在oldTab里面
        mCurrentTab = tab;
        doTabChanged(mCurrentTab, oldTab);

    }

    /**
     * 进行Fragment切换的真实的调度操作
     *
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                //将旧的Fragment从界面移除，但不销毁，还在Tab对象的缓存中
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab != null) {
            if (newTab.fragment == null) {
                //第一次创建
                Fragment fragment = Fragment.instantiate(mContext, newTab.clx.getName(), null);
                //缓存起来，用于后面切换Fragment而不是重新创建Fragment对象
                newTab.fragment = fragment;
                //提交到FragmentManager
                ft.add(mContainerId, fragment, newTab.clx.getName());
            } else {
                //若该Tab的Fragment对象已经存在
                //则从FragmentManager的缓存空间中加载到界面
                ft.attach(newTab.fragment);
            }
        }
        //提交，完成切换
        ft.commit();
        //回调，通知完成了Fragment切换
        notifyTabSelected(newTab, oldTab);

    }

    /**
     * 回调我们的监听器
     *
     * @param newTab 新的Tab<T>
     * @param oldTab 旧的Tab<T>
     */
    private void notifyTabSelected(Tab<T> newTab, Tab<T> oldTab) {

        if (mListener != null) mListener.onTabChanged(newTab, oldTab);
    }

    /**
     * 重复点击的操作
     *
     * @param tab
     */
    private void doTabReselected(Tab<T> tab) {

    }

    /**
     * 获取当前的显示的Tab
     *
     * @return 当前的Tab
     */
    public Tab<T> getCurrentTab() {
        return mCurrentTab;
    }


    /**
     * 代表底部导航栏的一个tab对象，包含对应Fragment
     * 用于管理Fragment与所需信息
     *
     * @param <T>
     */
    public static class Tab<T> {

        // Fragment对应的Class信息 (利用反射机制创建Fragment对象)
        public Class<? extends BaseFragment> clx;
        // 额外的字段，用户自己设定需要使用
        public T extra;
        // 内部缓存的对应的Fragment，
        // Package权限，外部无法使用
        Fragment fragment;

        public Tab(Class<? extends BaseFragment> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }
    }

    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
