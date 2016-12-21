package com.home.rw.mvp.ui.activitys.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;


import com.home.rw.R;
import com.home.rw.annotation.BindValues;
import com.home.rw.application.App;
import com.home.rw.di.component.ActivityComponent;
import com.home.rw.di.component.DaggerActivityComponent;
import com.home.rw.di.module.ActivityModule;
import com.home.rw.mvp.presenter.base.BasePresenter;
import com.home.rw.utils.DimenUtil;
import com.home.rw.utils.RxBus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by cty on 16/10/18.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;

    protected boolean mIsStatusTranslucent;

    protected ActivityComponent mActivityComponent;

    public abstract int getLayoutId();

    public abstract void initInjector();

    public abstract void initViews();

    protected Subscription mSubscription;

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAnnotation();
        initActivityComponent();
        int layout = getLayoutId();

        setContentView(layout);
        initInjector();
        ButterKnife.bind(this);

        setStatusBarTranslucent();
        //setStatusBarDarkMode(true,this);


    }
    private void initAnnotation() {
        if (getClass().isAnnotationPresent(BindValues.class)) {
            BindValues annotation = getClass().getAnnotation(BindValues.class);
            mIsStatusTranslucent = annotation.mIsStatusTranslucent();

        }
    }
    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null)
            mPresenter.onDestroy();
        RxBus.cancelSubscription(mSubscription);

    }
    public void setStatusBarTranslucent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }else{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (toolbar != null) {
                toolbar.setPadding(0, 0, 0, 0);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
                params.height = (int) DimenUtil.dp2px(44);
                toolbar.setLayoutParams(params);

            }
        }


    }
    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
