package com.keihong.imapp.module.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
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

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<BasePresenter, BaseModel> implements BottomNavigationView.OnNavigationItemSelectedListener {

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
    private NavTabHelper<String> mNavTabHelper;


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
        mNavTabHelper = new NavTabHelper<>(this, R.id.lay_container, getSupportFragmentManager(), new NavTabHelper.OnTabChangedListener<String>() {
            @Override
            public void onTabChanged(NavTabHelper.Tab<String> newTab, NavTabHelper.Tab<String> oldTab) {
                mTitle.setText(newTab.extra);

            }
        });
        mNavTabHelper.add(R.id.action_home, new NavTabHelper.Tab<String>(ActiveFragment.class, getResources().getString(R.string.action_home).toString()));
        mNavTabHelper.add(R.id.action_group, new NavTabHelper.Tab<String>(GroupFragment.class, getResources().getString(R.string.action_group).toString()));
        mNavTabHelper.add(R.id.action_contact, new NavTabHelper.Tab<String>(ContactFragment.class, getResources().getString(R.string.action_contact).toString()));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return mNavTabHelper.performClickMenu(item.getItemId());
    }
}

