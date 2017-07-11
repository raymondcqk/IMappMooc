package com.keihong.imapp.common.mvp;

import java.lang.reflect.ParameterizedType;

/**
 * 作者：luomin
 * 邮箱：asddavid@163.com
 */

public class MVPUtil {

    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ( (ParameterizedType)(o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
