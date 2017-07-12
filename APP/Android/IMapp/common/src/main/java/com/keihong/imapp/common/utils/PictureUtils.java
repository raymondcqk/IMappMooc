package com.keihong.imapp.common.utils;

import android.app.Activity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * @author KeiHong
 * @time 2017/7/12. 16:32
 * @description 图片相关工具类
 */

public class PictureUtils {

    /**
     * 居中裁剪图片并加载到AppBar背景
     * @param activity
     * @param view
     * @param resId
     */
    public static void setAppbarBg(Activity activity, View view, int resId) {
        Glide.with(activity).load(resId).centerCrop().into(new ViewTarget<View, GlideDrawable>(view) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });
    }
}
