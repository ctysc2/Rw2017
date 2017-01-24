package com.home.rw.mvp.ui.activitys.mineme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.listener.AlertDialogListener;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.CacheUtils;
import com.home.rw.utils.DateUtils;
import com.home.rw.utils.DialogUtils;

import java.text.DecimalFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements AlertDialogListener {

    private boolean clean_flag = false;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.tv_cache)
    TextView tvCacheSize;

    @OnClick({
            R.id.back,
            R.id.rl_verson,
            R.id.rl_cache,
            R.id.rl_feedback,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_verson:
                break;
            case R.id.rl_cache:
                mAlertDialog = DialogUtils.create(this,DialogUtils.TYPE_ALERT);
                mAlertDialog.show(this,getString(R.string.editExitHint1),getString(R.string.editExitHint2));
                //clear_cache();
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(this,FeedBackActivity.class));
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

    }

    @Override
    public void initViews() {
        midText.setText(R.string.setting);
        mback.setImageResource(R.drawable.btn_back);
        caculate_cache_size(clean_flag,tvCacheSize);
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
        clear_cache();
    }

    @Override
    public void onCancel() {
        mAlertDialog.dismiss();

    }
}
