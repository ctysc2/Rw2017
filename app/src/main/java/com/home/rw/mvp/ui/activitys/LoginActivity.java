package com.home.rw.mvp.ui.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.application.App;
import com.home.rw.greendao.entity.UserInfo;
import com.home.rw.greendao.gen.UserInfoDao;
import com.home.rw.greendaohelper.UserInfoDaoHelper;
import com.home.rw.listener.AnimationEndListener;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.presenter.impl.LoginPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.LoginView;
import com.home.rw.utils.DialogUtils;
import com.home.rw.utils.KeyBoardUtils;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.SystemTool;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

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
                mLoginPresenterImpl.beforeRequest();
                //mLoginPresenterImpl.processLogin(mEtName.getText().toString(),mEtPsw.getText().toString());
                if(mEtName.getText().toString().equals("1"))
                    mLoginPresenterImpl.processLogin("oa1_user1",mEtPsw.getText().toString());
                else
                    mLoginPresenterImpl.processLogin("oa_m",mEtPsw.getText().toString());
//                if(mEtName.getText().toString().equals("")){
//                    Toast.makeText(this,"请输入用户名",Toast.LENGTH_SHORT).show();
//                    return;
//                }

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
        switch (data.getCode()){
            case "ok":
                Toast.makeText(this,data.getMsg(),Toast.LENGTH_SHORT).show();
                containLoginData(data);
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            case "9997":
                Toast.makeText(this,getString(R.string.userNotExisted),Toast.LENGTH_SHORT).show();
                break;
            case "9998":
                Toast.makeText(this,getString(R.string.passWordError),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;


        }

    }
    //保存登录信息
    private void containLoginData(LoginEntity data) {

        long id = Long.parseLong(data.getData().getId());
        String session = data.getData().getSessionId();
        PreferenceUtils.setPrefLong(this,"ID",id);
        PreferenceUtils.setPrefString(this,"sessionID",session);
        PreferenceUtils.setPrefString(this,"userName",mEtName.getText().toString());
        PreferenceUtils.setPrefString(this,"passWord",mEtPsw.getText().toString());
        PreferenceUtils.setPrefString(this,"realname",data.getData().getRealname());
        PreferenceUtils.setPrefString(this,"avatar",data.getData().getAvatar());
        PreferenceUtils.setPrefString(this,"token",data.getData().getRongCloudToken());
//        Map<String,String> map = new HashMap<>();
//        map.put(UserInfoDaoHelper.USERNAME,name);
//        map.put(UserInfoDaoHelper.NICKNAME,nickname);
//        map.put(UserInfoDaoHelper.HEADURL,avatar);
//        UserInfoDaoHelper.getInstance().updateUserInfo(id,map);

    }

    @Override
    public void showProgress(int reqType) {
        mDialog = DialogUtils.create(this,DialogUtils.TYPE_COMMOM_LOADING);
        mDialog.show();

    }

    @Override
    public void hideProgress(int reqType) {
        mDialog.dismiss();
    }

    @Override
    public void showErrorMsg(int reqType,String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoginPresenterImpl!=null)
            mLoginPresenterImpl.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        new KeyBoardUtils(event,im,v).hideKeyBoardIfNecessary();
        return super.dispatchTouchEvent(event);
    }
}
