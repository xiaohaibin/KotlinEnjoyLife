package com.stx.xhb.core.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author: xiaohaibin.
 * @time: 2018/12/27
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe:
 */
public class AnimationUtils {

    /**
     * 从向右边边移动view，结束时设置为gone
     * @param view
     */
    public static void rightremove(final View view) {
        if (view == null) {
            return;
        }
        float width = view.getWidth() == 0 ? ScreenUtil.getScreenWidth(view.getContext()) : view.getWidth();

        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationX", 0, width);
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        oa.setDuration(300);
        oa.start();
    }

    /**
     * 从向右边边移动view
     * @param view
     */
    public static void rightshow(final View view) {
        if (view == null) {
            return;
        }
        float width = view.getWidth() == 0 ? ScreenUtil.getScreenWidth(view.getContext()) : view.getWidth();

        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationX", 0, -width);
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        oa.setDuration(300);
        oa.start();
    }

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
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        oa.setDuration(300);
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
        float width = view.getWidth() == 0 ? ScreenUtil.getScreenWidth(view.getContext()) : view.getWidth();

        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationY", 0, -width);
        oa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        oa.setDuration(300);
        oa.start();
    }
}
