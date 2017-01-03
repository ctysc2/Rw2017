package com.home.rw.mvp.ui.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

import com.home.rw.utils.DimenUtil;

/**
 * Created by cty on 2017/1/3.
 */

public class RecycleViewSperate extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {

            outRect.set(0, 0, 0, (int)DimenUtil.dp2px(10));

    }
}
