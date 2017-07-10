package com.photoselector.util;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.photoselector.R;


/**
 * Created by xiaozeng on 2015/11/15 0015.1
 *   自定义的提示  Toast
 *
 */
public class ToastView extends Toast {
    private static Toast mToast;
    public ToastView(Context context) {
        super(context);
    }

    /**
     *   自定义的 Toast 样式
     * @param context
     * @param resId
     * @param text
     * @param duration
     * @return
     * */
    private static void extentMakeText(Context context, int resId, CharSequence text, int duration) {
        //获取LayoutInflater对象
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout=inflater.inflate(R.layout.extent_toast_dialog,null);
        //实例化ImageView和TextView对象
        if (resId==0) {
            RelativeLayout imageviewlaout = (RelativeLayout) layout.findViewById(R.id.imageviewlaout);
            imageviewlaout.setVisibility(View.GONE);
        }else {
            ImageView toastImage = (ImageView) layout.findViewById(R.id.toast_img);
            toastImage.setImageResource(resId);
        }
        TextView toastText= (TextView) layout.findViewById(R.id.toast_text);

        toastText.setText(text);

        mHandler.removeCallbacks(r);
        if (mToast == null){//只有mToast==null时才重新创建，否则只需更改提示文字111
            mToast = new Toast(context);
            mToast.setView(layout);
            mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            mToast.setDuration(duration);
        }
        mHandler.postDelayed(r, duration);//延迟1秒隐藏toast
        mToast.show();
    }
    public static void showShort(Context context, int resId, String content) {
        extentMakeText(context, resId, content, 600);
    }
    public static void showCommentToast(Context context, int resId, String content) {
        extentMakeText(context, resId, content, 800);
    }
    public static void show(Context context, int resId, String content,int duration) {
        extentMakeText(context, resId, content, duration);
    }

    public static void showLong(Context context, int resId, String content) {
        extentMakeText(context, resId, content, 1500);
    }


    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast=null;//toast隐藏后，将其置为null
        }
    };

}
