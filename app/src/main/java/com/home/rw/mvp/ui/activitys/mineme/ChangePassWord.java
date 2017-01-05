package com.home.rw.mvp.ui.activitys.mineme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.KeyBoardUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePassWord extends BaseActivity {
    private int Second = 60;

    Timer mTimer;

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.bt_getVerifi)
    Button mButton;

    @BindView(R.id.et1)
    EditText midText1;

    @BindView(R.id.et2)
    EditText midText2;

    @BindView(R.id.et3)
    EditText midText3;

    @BindView(R.id.tv_phoneNum)
    TextView mTvPhone;

    private String phone = "13636315569";

    @OnClick({
            R.id.back,
            R.id.bt_getVerifi,
            R.id.iv1,
            R.id.iv2,
            R.id.iv3,
    })
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.bt_getVerifi:
                countDownInSecond();
                break;
            case R.id.iv1:
                midText1.setText("");
                break;
            case R.id.iv2:
                midText2.setText("");
                break;
            case R.id.iv3:
                midText3.setText("");
                break;
            default:
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass_word;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(R.string.chagePsw);
        mback.setImageResource(R.drawable.btn_back);
        mTvPhone.setText(phone.substring(0,3)+"****"+phone.substring(7,phone.length()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void countDownInSecond() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mButton.setEnabled(false);
            }
        });

        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkTime();
            }
        }, 0, 1000);
    }

    private void checkTime() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Second == 0) {
                    if(mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                        mButton.setText(getString(R.string.verifiCode));
                        mButton.setEnabled(true);
                        Second = 60;
                    }
                    return;
                }
                mButton.setText(String.format(getString(R.string.verifiCodeRetry),Second));
                Second--;
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
