package com.home.rw.mvp.ui.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by cty on 2016/12/20.
 */

public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    public static final int COMMON = -1;
    public static final int APPROVEMENT_LIST_CELL = 0;

    private Drawable mDivider;

    private int mOrientation;

    private int mCellType;

    public RecycleViewDivider(Context context, int orientation,int type) {

        this.mCellType = type;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }


    }
    private void drawVertical(Canvas c, RecyclerView parent){
        switch (mCellType){
            case APPROVEMENT_LIST_CELL:
                drawVerticalApprovement(c,parent);
                break;
            default:
                drawVerticalCommon(c,parent);

        }

    }

    private void drawHorizontal(Canvas c, RecyclerView parent){
        switch (mCellType){
            case APPROVEMENT_LIST_CELL:
            default:
                drawHorizontalCommon(c,parent);

        }

    }
    //审核列表
    private void drawVerticalApprovement(Canvas c, RecyclerView parent){
        Log.i("cty","drawVerticalApprovement");
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();

            final int left = child.getPaddingLeft();
            final int right = child.getWidth() - child.getPaddingLeft();
            Log.i("cty","left:"+left);
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
        Log.i("cty","childCount:"+childCount);

    }




    public void drawVerticalCommon(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        Log.i("cty","drawVerticalCommon");
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if(i == childCount-1)
                break;
            final View child = parent.getChildAt(i);
            RecyclerView v = new RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontalCommon(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
