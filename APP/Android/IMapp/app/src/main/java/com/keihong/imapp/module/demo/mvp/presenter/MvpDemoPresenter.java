package com.keihong.imapp.module.demo.mvp.presenter;

import com.keihong.imapp.module.demo.mvp.DemoMVPContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author 陈其康
 */

public class MvpDemoPresenter extends DemoMVPContract.Presenter {


    @Override
    public void rxLogin(String userName, String passWord) {
        mView.showLoading();
        mModel.rxLogin(userName, passWord)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.success(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.failed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }


    @Override
    public void clear() {
        mView.clear();
    }
}
