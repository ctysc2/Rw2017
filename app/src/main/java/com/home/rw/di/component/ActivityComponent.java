package com.home.rw.di.component;

import android.app.Activity;
import android.content.Context;


import com.home.rw.di.module.ActivityModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerActivity;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;

import dagger.Component;

/**
 * Created by cty on 16/10/19.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
}