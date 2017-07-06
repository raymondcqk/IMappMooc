package com.keihong.imapp;

import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends com.keihong.imapp.common.app.Activity {


    @BindView(R.id.txt_test)
    TextView mTestText;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestText.setText("Test Hello");
    }
}
