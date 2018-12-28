package com.stx.xhb.core.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author: xiaohaibin.
 * @time: 2018/12/27
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
public class AnimationUtils {

    /**
     * 从向下边移动view，结束时设置为gone
     * @param view
     */
    public static void bottomremove(final View view) {
        if (view == null) {
            return;
        }
        float width = view.getWidth() == 0 ? ScreenUtil.getScreenWidth(view.getContext()) : view.getWidth();
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationY", 0, width);
        oa.setInterpolator(new LinearInterpolator());
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        oa.setDuration(500);
        oa.start();
    }

    /**
     * 从下向上边移动view
     * @param view
     */
    public static void bottomshow(final View view) {
        if (view == null) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        float translationY = view.getTranslationY();
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationY", translationY, 0);
        oa.setInterpolator(new LinearInterpolator());
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }
        });
        oa.setDuration(500);
        oa.start();
    }
}
