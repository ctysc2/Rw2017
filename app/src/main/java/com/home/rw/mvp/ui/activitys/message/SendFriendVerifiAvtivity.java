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
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.mvp.entity.base.BaseEntity;
import com.home.rw.mvp.presenter.impl.AddFriendPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.AddFriendView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SendFriendVerifiAvtivity extends BaseActivity implements AddFriendView{

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

    @Inject
    AddFriendPresenterImpl mAddFriendPresenterImpl;

    private String userId;

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
                mAddFriendPresenterImpl.beforeRequest();
                mAddFriendPresenterImpl.addFriend(userId,mEt1.getText().toString());
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
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        midText.setText(getString(R.string.friendVerifi));
        rightText.setText(getString(R.string.send));
        mAddFriendPresenterImpl.attachView(this);
        mback.setImageResource(R.drawable.btn_back);
        userId = getIntent().getStringExtra("userId");
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

    @Override
    public void addFriendCompleted(BaseEntity data) {
        if(data.getCode().equals("ok")){
            Toast.makeText(this,getString(R.string.sentAddFriend),Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void showProgress(int reqType) {
        if(mLoadDialog == null){
            mLoadDialog = DialogUtils.create(this,DialogUtils.TYPE_ADD);
            mLoadDialog.show();
        }

    }

    @Override
    public void hideProgress(int reqType) {
        if(mLoadDialog!=null){
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {

    }
}
