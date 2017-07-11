package com.keihong.imapp.module.login.mvp.presenter;

import com.keihong.imapp.module.login.mvp.LoginContract;


/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class LoginPresenter extends LoginContract.Presenter {


    @Override
    protected void rxLogin(String userName, String passWord) {
//        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
//            Toast.makeText(mContext, "请输入你的用户名和密码", Toast.LENGTH_SHORT).show();
//        } else {
//            mView.showLoading();
//           mModel.rxLogin(userName,passWord)
//                   .subscribeOn(Schedulers.io())
//                   .observeOn(AndroidSchedulers.mainThread())
//                   .subscribe(new Subscriber<String>() {
//                       @Override
//                       public void onCompleted() {
//                           mView.hideLoading();
//                       }
//
//                       @Override
//                       public void onError(Throwable e) {
//                           mView.hideLoading();
//
//                       }
//
//                       @Override
//                       public void onNext(String s) {
//                           mView.hideLoading();
//                           Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
//
//
//                       }
//                   });
//        }
    }


    @Override
    protected void clear() {
        mView.clear();
    }
}
