package com.keihong.imapp.module.demo.mvp.model;


import com.keihong.imapp.module.demo.mvp.DemoMVPContract;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class MvpDemoModel implements DemoMVPContract.Model {

    @Override
    public Observable<String> rxLogin(final String userName, final String passWord) {
        //观察者模式,这里产生事件,事件产生后发送给接受者,但是一定要记得将事件的产生者和接收者捆绑在一起,否则会出现错误
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //这里调用的方法会在产生事件之后会发送给接收者,接收者对应方法会收到

                Thread.sleep(2000);
                if (userName.equals("123")) {
                    e.onNext("用户名正确");
                } else {
                    e.onError(new IllegalArgumentException("用户名错误"));
                    return;
                }

                Thread.sleep(2000);
                if (passWord.equals("123")) {
                    e.onNext("密码正确");
                } else {
                    e.onError(new IllegalArgumentException("密码错误"));
                    return;
                }

                e.onComplete();


            }
        });

    }


}
