package com.home.rw.di.component;

import android.app.Activity;
import android.content.Context;


import com.home.rw.di.module.ActivityModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerActivity;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.work.ApprovedByMeActivity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.DailyLogActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.ProposeFromMeActivity;
import com.home.rw.mvp.ui.activitys.work.RollMeActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;

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
    void inject(ApprovedByMeActivity bymeActivity);
    void inject(ProposeFromMeActivity frommeActivity);
    void inject(ExtraWorkActivity extraWorkActivity);
    void inject(RollMeActivity rollmeActivity);
    void inject(GetOutActivity getoutActivity);
    void inject(WipedActivity wipedActivity);
    void inject(AskForLeaveActivity askforleaveActivity);
    void inject(DailyLogActivity dailyLogActivity);


}