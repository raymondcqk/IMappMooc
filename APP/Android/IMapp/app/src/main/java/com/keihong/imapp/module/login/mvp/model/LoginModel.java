package com.keihong.imapp.module.login.mvp.model;

import android.database.Observable;
import android.os.Handler;

import com.keihong.imapp.module.login.mvp.LoginContract;


/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public void login(final String userName, final String passWord, final UserLoginListener userLoginListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("jike".equals(userName) && "123".equals(passWord)) {
                    userLoginListener.loginSuccess();

                } else {
                    userLoginListener.loginFailed();
                }
            }
        }, 2000);
    }

    @Override
    public Observable<String> rxLogin(final String userName, final String passWord) {
        return null;
//                Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                SystemClock.sleep(2000);
//                if ("jike".equals(userName) && "123".equals(passWord)) {
//                    subscriber.onNext("SUCCESS");
//                    subscriber.onCompleted();
//                } else {
//                    subscriber.onNext("FAILED");
//                    subscriber.onCompleted();
//                }
//
//            }
//        });
    }


}
