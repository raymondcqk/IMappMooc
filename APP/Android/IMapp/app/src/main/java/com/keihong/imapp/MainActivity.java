package com.keihong.imapp;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends com.keihong.imapp.common.app.Activity {


    @BindView(R.id.txt_test)
    TextView mTestText;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestText.setText("Test Hello");
    }

    @Override
    protected void initData() {
        super.initData();


    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {

    }


}
