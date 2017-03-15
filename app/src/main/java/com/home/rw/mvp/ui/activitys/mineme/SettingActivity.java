package com.home.rw.mvp.ui.activitys.mineme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.common.HostType;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.LogOutPresenterImpl;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.LogOutView;
import com.home.rw.utils.CacheUtils;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.PreferenceUtils;

import java.text.DecimalFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements AlertDialogListener,LogOutView {

    private boolean clean_flag = false;
    private int mDialogType = 0;
    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_cache)
    TextView tvCacheSize;

    @Inject
    LogOutPresenterImpl mLogOutPresenterImpl;
    @OnClick({
            R.id.back,
            R.id.rl_verson,
            R.id.rl_cache,
            R.id.rl_feedback,
            R.id.bt_logout
    })
    public void OnClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_verson:
                break;
            case R.id.rl_cache:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.isClearCache));
                mDialogType = 1;
                //clear_cache();
                break;
            case R.id.rl_feedback:
                intent = new Intent(this,FeedBackActivity.class);
                intent.putExtra("entry", HostType.MY_FEEDBACK);
                startActivity(intent);
                break;
            case R.id.bt_logout:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.isLogOut));
                mDialogType = 2;
                break;
            default:
                break;


        }

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(R.string.setting);
        mback.setImageResource(R.drawable.btn_back);
        caculate_cache_size(clean_flag,tvCacheSize);
        mLogOutPresenterImpl.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }

    private void clear_cache() {
        //清除内部及外部的cache文件夹下面缓存文件
        CacheUtils.deleteFile(getCacheDir());
        CacheUtils.deleteFile(getExternalCacheDir());
        clean_flag = true;
        caculate_cache_size(true, tvCacheSize);
    }

    private void caculate_cache_size(boolean clean_flag, TextView mCacheSize) {
        // false:表示还没清除缓存的标志
        // true：表示已经清除缓存的标志
        if (clean_flag) {
            mCacheSize.setText(0 + "KB");
            this.clean_flag = false;
        } else {
            try {
                long cacheSize = CacheUtils.getFolderSize(getCacheDir()) +
                        CacheUtils.getFolderSize(getExternalCacheDir());
                if (cacheSize >= (1024 * 1024)) {
                    float num = (float) cacheSize / (1024 * 1024);
                    mCacheSize.setText(new DecimalFormat("0.00").format(num) + "MB");
                } else {
                    mCacheSize.setText(cacheSize / 1024 + "KB");
                }
            } catch (Exception e) {
                Log.e("clean cache", e.getMessage());
            }
        }

    }

    @Override
    public void onConFirm() {
        mAlertDialog.dismiss();
        if(mDialogType == 1){
            clear_cache();
        }else{
            doLogOut();
        }
        
    }

    private void doLogOut() {
        mLogOutPresenterImpl.logOut();
    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();

    }

    @Override
    public void logOutCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            PreferenceUtils.setPrefLong(this,"ID",0);
            PreferenceUtils.setPrefString(this,"sessionID","");
            PreferenceUtils.setPrefString(this,"userName","");
            PreferenceUtils.setPrefString(this,"passWord","");
            PreferenceUtils.setPrefString(this,"realname","");
            PreferenceUtils.setPrefString(this,"avatar","");
            PreferenceUtils.setPrefString(this,"token","");
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this, DialogUtils.TYPE_UPDATE);
            mLoadDialog.show();
        }
    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog != null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        Toast.makeText(this,getString(R.string.commitFailed),Toast.LENGTH_SHORT).show();
    }
}
