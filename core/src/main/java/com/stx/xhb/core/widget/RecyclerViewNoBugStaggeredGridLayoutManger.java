package com.stx.xhb.core.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * @author: xiaohaibin.
 * @time: 2018/3/28
 * @mail:xhb_199409@163.com
 * @github:https://github.com/xiaohaibin
 * @describe: fixed RecyclerView notifyDataChanged bug
 */

public class RecyclerViewNoBugStaggeredGridLayoutManger extends StaggeredGridLayoutManager {

    public RecyclerViewNoBugStaggeredGridLayoutManger(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public RecyclerViewNoBugStaggeredGridLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        try {
            super.onLayoutChildren( recycler, state );
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
