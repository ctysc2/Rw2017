package com.photoselector.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.photoselector.R;

import java.lang.reflect.Method;


public class MyPopupWindow extends PopupWindow
{
    private Button btn_save;
    private  Activity context;
    private RelativeLayout layout;


    private Button btn_cancel;
    private View mMenuView;

    public MyPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.my_dialog, null);
        btn_save = (Button) mMenuView.findViewById(R.id.btn_save);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        layout = (RelativeLayout)mMenuView.findViewById(R.id.layout);
        btn_save.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(LayoutParams.MATCH_PARENT);

        this.setHeight(LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);

        ColorDrawable dw = new ColorDrawable(0xb0000000);

        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        setLayoutHeight();
        this.showAtLocation(btn_cancel, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void setLayoutHeight() {

        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.bottomMargin = hasVisible(context);
        layout.setLayoutParams(params);

    }

    /**
     * 获取屏幕的真实高度，包括虚拟返回键
     * @return
     */
    public static int hasVisible(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        Class classType;
        try {
            classType = Class.forName("android.view.Display");
            Method method = classType.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, metrics);
        } catch (Exception e) {
        }
        Log.e("pixels","虚拟返="+(metrics.heightPixels - context.getWindowManager().getDefaultDisplay().getHeight()));
        return metrics.heightPixels - context.getWindowManager().getDefaultDisplay().getHeight();
    }
}
