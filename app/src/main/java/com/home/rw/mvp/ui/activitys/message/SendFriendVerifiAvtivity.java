package com.home.rw.mvp.ui.activitys.message;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.rw.R;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.utils.KeyBoardUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SendFriendVerifiAvtivity extends BaseActivity {

    @BindView(R.id.back)
    ImageButton mback;

    @BindView(R.id.midText)
    TextView midText;

    @BindView(R.id.rightText)
    TextView rightText;

    @BindView(R.id.et1)
    EditText mEt1;

    @BindView(R.id.iv_del)
    ImageView mDelete;

    @OnClick({R.id.back,
            R.id.iv_del,
            R.id.rightText
    })
    public void onClick(View v){
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rightText:
                finish();
                break;
            case R.id.iv_del:
                mEt1.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_friend_verifi_avtivity;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.friendVerifi));
        rightText.setText(getString(R.string.send));
        mback.setImageResource(R.drawable.btn_back);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }

}
