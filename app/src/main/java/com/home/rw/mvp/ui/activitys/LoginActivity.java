package com.home.rw.mvp.ui.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.listener.AnimationEndListener;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.presenter.impl.LoginPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.LoginView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.SystemTool;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {
    private  DialogUtils mDialog;
    @BindView(R.id.bt_login)
    Button mBtnLogin;

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_psw)
    EditText mEtPsw;

    @Inject
    LoginPresenterImpl mLoginPresenterImpl;

    @OnClick({R.id.bt_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_login:
                //mLoginPresenterImpl.beforeRequest();
                //mLoginPresenterImpl.processLogin(mEtName.getText().toString(),mEtPsw.getText().toString());
                startActivity(new Intent(this,MainActivity.class));
                break;
            default:
                break;
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

        mPresenter = mLoginPresenterImpl;
        mPresenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

    }

    @Override
    public void loginCompleted(LoginEntity data) {
        Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        mDialog = DialogUtils.create(this,DialogUtils.TYPE_COMMOM_LOADING);
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }
}
