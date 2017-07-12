package com.keihong.imapp.module.demo.mvp;


import com.keihong.imapp.common.mvp.BaseModel;
import com.keihong.imapp.common.mvp.BasePresenter;
import com.keihong.imapp.common.mvp.BaseView;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class DemoMVPContract {

    public interface Model extends BaseModel {
        io.reactivex.Observable<String> rxLogin(String userName, String passWord);
    }

    public interface View extends BaseView {
        void success(String next);

        void failed(String error);

        void clear();
    }

    public abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract  void rxLogin(String userName, String passWord);
        public abstract void clear();
    }
}
