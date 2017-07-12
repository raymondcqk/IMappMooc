package com.keihong.imapp.module.main;

import android.support.design.widget.BottomNavigationView;
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

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<BasePresenter, BaseModel> {

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

    @OnClick(R.id.im_search)
    void onSearchClick(){
        Toast.makeText(activity, "search", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.im_portrait)
    void onPortraitClick(){
        Toast.makeText(activity, "portrait", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_action)
    void onFloatBtnClick(){
        Toast.makeText(activity, "FloatActionButton", Toast.LENGTH_SHORT).show();
    }

}

