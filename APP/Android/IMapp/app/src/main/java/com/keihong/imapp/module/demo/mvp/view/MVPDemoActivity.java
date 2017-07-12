package com.keihong.imapp.module.demo.mvp.view;

import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.keihong.imapp.R;
import com.keihong.imapp.common.app.BaseActivity;
import com.keihong.imapp.module.demo.mvp.DemoMVPContract;
import com.keihong.imapp.module.demo.mvp.model.MvpDemoModel;
import com.keihong.imapp.module.demo.mvp.presenter.MvpDemoPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MVPDemoActivity extends BaseActivity<MvpDemoPresenter, MvpDemoModel>
        implements DemoMVPContract.View {


    @BindView(R.id.txt_test)
    TextView mTestText;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.edt_account)
    EditText mEdtAccount;
    @BindView(R.id.edt_passwd)
    EditText mEdtPasswd;
    private ProgressDialog progressDialog;


//    @Override
//    protected void initPresenter() {
//        mPresenter.setViewAndModel(this, mModel);
//    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mvp_demo;
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


    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void success(String next) {
        mTestText.setText(next);
    }

    @Override
    public void failed(String error) {
        mTestText.setText(error);
    }

    @Override
    public void clear() {
        mEdtAccount.setText("");
        mEdtPasswd.setText("");
    }

    @OnClick(R.id.btn_submit)
    void onSubmit() {
        mPresenter.rxLogin(mEdtAccount.getText().toString(), mEdtPasswd.getText().toString());
    }

    @OnClick(R.id.btn_clear)
    void onClear() {
        mPresenter.clear();
    }
}
