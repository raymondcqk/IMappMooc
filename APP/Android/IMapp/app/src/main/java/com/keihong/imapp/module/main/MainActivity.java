package com.keihong.imapp.module.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keihong.imapp.R;
import com.keihong.imapp.common.app.BaseActivity;
import com.keihong.imapp.common.mvp.BaseModel;
import com.keihong.imapp.common.mvp.BasePresenter;
import com.keihong.imapp.common.utils.PictureUtils;
import com.keihong.imapp.common.widget.PortraitView;
import com.keihong.imapp.module.main.fragments.main.ActiveFragment;
import com.keihong.imapp.module.main.fragments.main.ContactFragment;
import com.keihong.imapp.module.main.fragments.main.GroupFragment;
import com.keihong.imapp.module.main.helper.NavTabHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<BasePresenter, BaseModel> implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavTabHelper.OnTabChangedListener<Integer> {

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;
    private NavTabHelper<Integer> mNavTabHelper;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        //居中裁剪图片并加载到appbar的背景
        PictureUtils.setAppbarBg(this, mLayAppbar, R.drawable.bg_src_morning);
    }

    @Override
    protected void initLinstener() {
        super.initLinstener();
        mNavigation.setOnNavigationItemSelectedListener(this);

        //初始化底部辅助工具类
        initNavTabHelper();
    }

    @Override
    protected void initData() {
        super.initData();
        //获取底部导航的menu对象，触发点击操作，模拟点击了home，跟手动点击效果一样，会执行onNavigationItemSelected()
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    /**
     * 初始化底部辅助工具类
     */
    private void initNavTabHelper() {
        mNavTabHelper = new NavTabHelper<>(this, R.id.lay_container, getSupportFragmentManager(), this);

        mNavTabHelper
                .add(R.id.action_home,
                        new NavTabHelper.Tab<>(ActiveFragment.class, R.string.action_home))
                .add(R.id.action_group,
                        new NavTabHelper.Tab<>(GroupFragment.class, R.string.action_group))
                .add(R.id.action_contact,
                        new NavTabHelper.Tab<>(ContactFragment.class, R.string.action_contact));
//        getResources().getString(R.string.action_home).toString()
    }

    @OnClick(R.id.im_search)
    void onSearchClick() {
        Toast.makeText(activity, "search", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        Toast.makeText(activity, "portrait", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_action)
    void onFloatBtnClick() {
        Toast.makeText(activity, "FloatActionButton", Toast.LENGTH_SHORT).show();
    }

    /**
     * 当我们的底部导航被点击的时候触发
     *
     * @param item MenuItem
     * @return True 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 转接事件流到工具类中
        return mNavTabHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 就的Tab
     */
    @Override
    public void onTabChanged(NavTabHelper.Tab<Integer> newTab, NavTabHelper.Tab<Integer> oldTab) {
        //根据Tab里面记录的StringId（标题Id）修改标题
        mTitle.setText(newTab.extra);
        //给浮动按钮添加动画
        floatActionButtonAnimate(newTab);


    }

    /**
     * 给浮动按钮添加动画
     *
     * @param newTab
     */
    private void floatActionButtonAnimate(NavTabHelper.Tab<Integer> newTab) {
        //添加浮动按钮动画
        float transY = 0;
        float rotation = 360;
        if (newTab.extra.equals(R.string.action_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else if (newTab.extra.equals(R.string.action_group)) {
            mAction.setImageResource(R.drawable.ic_group_add);
            rotation = -360;
        } else {
            mAction.setImageResource(R.drawable.ic_contact_add);
            rotation = 360;
        }

        //开始动画
        //旋转--> y轴位移 --> 弹性差值器 --> 动画时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateInterpolator(1))
                .setDuration(380)
                .start();
    }
}

