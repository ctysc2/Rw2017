package com.home.rw.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.listener.AnimationEndListener;
import com.home.rw.listener.OnKeyClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cty on 2016/12/12.
 */

public class DialogUtils {
    public static final int TYPE_COMMOM_LOADING = 0;
    public static final int TYPE_ALERT = 1;
    public static final int TYPE_UPDATE = 2;
    public static final int TYPE_LOADING = 3;
    public static final int TYPE_DEL = 4;
    public static final int TYPE_CREATE = 5;
    public static final int TYPE_ADD = 6;
    public static final int TYPE_DOOR_SEARCH = 7;
    public static final int TYPE_DOOR_OPEN= 8;
    public static final int TYPE_KEY_LIST = 9;

    private  Context mContext;
    private  int mDialogType;
    private Dialog mDialog;
    private int resID = R.string.loading;
    public static  DialogUtils create(Context context,int dialogType){

        return new DialogUtils(context,dialogType);


    }
    private DialogUtils(Context context,int dialogType){
        mContext = context;
        mDialogType = dialogType;
    }
    public boolean isShowing(){
        return mDialog.isShowing();

    }
    public void show(){
        switch (mDialogType){
            case TYPE_COMMOM_LOADING:
                resID = R.string.loging;
                break;
            case TYPE_UPDATE:
                resID = R.string.updating;
                break;
            case TYPE_LOADING:
                resID = R.string.loading;
                break;
            case TYPE_DEL:
                resID = R.string.deleteing;
                break;
            case TYPE_CREATE:
                resID = R.string.createing;
                break;
            case TYPE_ADD:
                resID = R.string.addingFriend;
                break;
            case TYPE_DOOR_SEARCH:
                resID = R.string.searching;
                break;
            case TYPE_DOOR_OPEN:
                resID = R.string.opening;
                break;
            default:
                break;

        }
        showLoadingDialog();
    }
    public void show(AlertDialogListener listenerString ,String hint1){

        switch (mDialogType){
            case TYPE_ALERT:
                showAlertDialog(listenerString,hint1);
            default:
                break;
        }

    }
    public void show(ArrayList<String> keyList,OnKeyClickListener listener){
        showKeyList(keyList,listener);
    }
    public void show(AlertDialogListener listenerString,String hint1,String hint2){

        switch (mDialogType){
            case TYPE_ALERT:
                showAlertDialog(listenerString,hint1,hint2);
            default:
                break;
        }

    }
    public void dismiss(){
        if(mDialog!=null)
            mDialog.dismiss();


    }
    private TextView mHint;

    public void setText(int resID){
        if(mHint!=null)
            mHint.setText(mContext.getString(resID));
    }
    //加载中弹框
    private void showLoadingDialog(){

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.common_loading, null);
        mHint = ((TextView)contentView.findViewById(R.id.tv_loading));
        mHint.setText(mContext.getString(resID));
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
//        if(resID == R.string.loging)
            mDialog.setCancelable(false);
        mDialog.setContentView(contentView);
        Window window = mDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent); //背景透明

        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.5);
        window.setAttributes(params);
        window.setWindowAnimations(R.style.loadingPopinStyle);

    }
    //单行提示框
    private  void showAlertDialog(final AlertDialogListener listenerString,String hint1){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.alert_dialog, null);
        ((RelativeLayout)contentView.findViewById(R.id.Rl_layout1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onCancel();
            }
        });
        ((RelativeLayout)contentView.findViewById(R.id.rl_layout2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onConFirm();
            }
        });
        TextView mhint1 = (TextView)contentView.findViewById(R.id.Tv_hint1);
        TextView mhint2 = (TextView)contentView.findViewById(R.id.Tv_hint2);
        mhint1.setText(hint1);
        mhint2.setVisibility(View.GONE);
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setContentView(contentView);
        Window window = mDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.8);
        window.setAttributes(params);

    }
    //两行提示框
    private  void showAlertDialog(final AlertDialogListener listenerString,String hint1,String hint2){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.alert_dialog, null);
        ((RelativeLayout)contentView.findViewById(R.id.Rl_layout1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onCancel();
            }
        });
        ((RelativeLayout)contentView.findViewById(R.id.rl_layout2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerString.onConFirm();
            }
        });
        TextView mhint1 = (TextView)contentView.findViewById(R.id.Tv_hint1);
        TextView mhint2 = (TextView)contentView.findViewById(R.id.Tv_hint2);
        mhint1.setText(hint1);
        mhint2.setText(hint2);
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCancelable(true);
        mDialog.setContentView(contentView);
        Window window = mDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.8);
        window.setAttributes(params);

    }

    private void showKeyList(ArrayList<String> keyList,final OnKeyClickListener listener){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View contentView = inflater.inflate(R.layout.keylist, null);
        TextView mCancle = (TextView)contentView.findViewById(R.id.tv_cancle);
        ListView mListview = (ListView) contentView.findViewById(R.id.lv_list);
        mListview.setAdapter(new ArrayAdapter<String>(mContext,
                R.layout.item_key, keyList));
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onKeyClick(position);
            }
        });
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onKeyClick(-1);
            }
        });
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.setCancelable(false);
        mDialog.setContentView(contentView);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = (int)(DimenUtil.getScreenWidth()*0.8);
        window.setAttributes(params);

    }
}
