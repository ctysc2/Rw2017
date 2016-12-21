package com.home.rw.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.home.rw.R;
import com.home.rw.listener.AnimationEndListener;

/**
 * Created by cty on 2016/12/12.
 */

public class DialogUtils {
    public static final int TYPE_COMMOM_LOADING = 0;
    private static Context mContext;
    private static int mDialogType;
    private Dialog mDialog;
    public static  DialogUtils create(Context context,int dialogType){
        mContext = context;
        mDialogType = dialogType;
        return new DialogUtils();

    }

    public void show(){
        switch (mDialogType){
            case TYPE_COMMOM_LOADING:
                showLoadingDialog();
                break;
            default:
                break;
        }

    }
    public void dismiss(){
        if(mDialog!=null)
            mDialog.dismiss();
        mDialog = null;

    }
    private void showLoadingDialog(){

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.common_loading, null);

        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        //mDialog.setCancelable(false);
        mDialog.setContentView(contentView);
        Window window = mDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent); //背景透明

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.6);
        window.setAttributes(params);
        window.setWindowAnimations(R.style.loadingPopinStyle);

    }
}
