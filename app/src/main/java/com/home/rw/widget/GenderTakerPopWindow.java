package com.home.rw.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.home.rw.R;

/**
 * Created by cty on 2017/1/3.
 */

public class GenderTakerPopWindow extends PopupWindow {
    private Button btn_male, btn_female, btn_cancel;
    private View mMenuView;
    private Activity context;
    public GenderTakerPopWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_genderselect, null);
        btn_male = (Button) mMenuView.findViewById(R.id.btn_male);
        btn_female = (Button) mMenuView.findViewById(R.id.btn_female);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                preDismiss();
            }
        });

        btn_male.setOnClickListener(itemsOnClick);
        btn_female.setOnClickListener(itemsOnClick);
        btn_cancel.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.headerTakerStyle);

        ColorDrawable dw = new ColorDrawable(0x00000000);


        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        preDismiss();
                    }
                }
                return true;
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                preDismiss();
            }
        });
    }

    private void preShow(){
        Window mWindow = context.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 0.4f;
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mWindow.setAttributes(lp);
    }

    private void preDismiss(){
        Window mWindow = context.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.alpha = 1.0f;
        mWindow.setAttributes(lp);
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dismiss();

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        preShow();
        super.showAtLocation(parent, gravity, x, y);
    }
}
