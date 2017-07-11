package com.keihong.imapp.module.login.mvp.view;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.keihong.imapp.R;
import com.keihong.imapp.common.app.BaseActivity;
import com.keihong.imapp.module.login.mvp.LoginContract;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginBaseActivity<LoginPresenter, LoginModel> extends BaseActivity implements LoginContract.View {


    @BindView(R.id.txt_test)
    TextView mTestText;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;

    private ProgressDialog progressDialog;


//    @Override
//    protected void initPresenter() {
//        mPresenter.setViewAndModel(this, mModel);
//    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestText.setText("Test Hello");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登录中,请稍后");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void initLinstener() {

    }

    @Override
    protected void initData() {
        super.initData();


    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {

    }


    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void success() {

    }

    @Override
    public void failed() {

    }

    @Override
    public void clear() {

    }
}
