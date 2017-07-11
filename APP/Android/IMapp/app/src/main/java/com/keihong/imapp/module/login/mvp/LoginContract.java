package com.keihong.imapp.module.login.mvp;


import android.database.Observable;

import com.keihong.imapp.common.mvp.BaseModel;
import com.keihong.imapp.common.mvp.BasePresenter;
import com.keihong.imapp.common.mvp.BaseView;
import com.keihong.imapp.module.login.mvp.model.UserLoginListener;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class LoginContract {

    public interface Model extends BaseModel {
        void login(String userName, String passWord, UserLoginListener userLoginListener);
        Observable<String> rxLogin(String userName, String passWord);
    }

    public interface View extends BaseView {
        void success();

        void failed();

        void clear();
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        protected abstract  void rxLogin(String userName, String passWord);
        protected abstract void clear();
    }
}
