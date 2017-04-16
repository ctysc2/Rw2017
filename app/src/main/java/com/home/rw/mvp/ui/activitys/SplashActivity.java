package com.home.rw.mvp.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.home.rw.R;
import com.home.rw.mvp.entity.LoginEntity;
import com.home.rw.mvp.presenter.impl.LoginPresenterImpl;
import com.home.rw.mvp.ui.activitys.base.BaseActivity;
import com.home.rw.mvp.view.LoginView;
import com.home.rw.utils.PreferenceUtils;
import com.home.rw.utils.TransformUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

public class SplashActivity extends BaseActivity implements LoginView {
    @Inject
    LoginPresenterImpl mLoginPresenterImpl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }
    private void doLogin(String username,String password){
        mLoginPresenterImpl.processLogin(username,password);
    }
    @Override
    public void initViews() {
        //过3秒执行下一步
        Observable.timer(1, TimeUnit.SECONDS).compose(TransformUtils.<Object>defaultSchedulers())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                        String userName = PreferenceUtils.getPrefString(SplashActivity.this,"userName","");
                        String passWord = PreferenceUtils.getPrefString(SplashActivity.this,"passWord","");

                        if(userName.equals("") || passWord.equals("")){
                            //未登录场合跳转登录界面
                            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        }else{
                            doLogin(userName,passWord);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object data) {

                    }

                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        mLoginPresenterImpl.attachView(this);
    }

    @Override
    public void loginCompleted(LoginEntity data) {
        if(data.getCode().equals("ok")){
            long id = Long.parseLong(data.getData().getId());
            String session = data.getData().getSessionId();
            PreferenceUtils.setPrefLong(this,"ID",id);
            PreferenceUtils.setPrefString(this,"sessionID",session);
            PreferenceUtils.setPrefString(this,"realname",data.getData().getRealname());
            PreferenceUtils.setPrefString(this,"avatar",data.getData().getAvatar());
            PreferenceUtils.setPrefString(this,"token",data.getData().getRongCloudToken());
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        }
        finish();
    }

    @Override
    public void showProgress(int reqType) {

    }

    @Override
    public void hideProgress(int reqType) {

    }

    @Override
    public void showErrorMsg(int reqType, String msg) {
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        finish();
    }
}
