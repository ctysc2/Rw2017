package com.home.rw.di.component;

import android.app.Activity;
import android.content.Context;


import com.home.rw.di.module.ActivityModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerActivity;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeActivity;
import com.home.rw.mvp.ui.activitys.mineme.ChangePassWord;
import com.home.rw.mvp.ui.activitys.mineme.FeedBackActivity;
import com.home.rw.mvp.ui.activitys.mineme.OrderActivity;
import com.home.rw.mvp.ui.activitys.mineme.WalletActivity;
import com.home.rw.mvp.ui.activitys.work.ApprovedByMeActivity;
import com.home.rw.mvp.ui.activitys.work.AskForLeaveActivity;
import com.home.rw.mvp.ui.activitys.work.CardActivity;
import com.home.rw.mvp.ui.activitys.work.DailyLogActivity;
import com.home.rw.mvp.ui.activitys.work.ExtraWorkActivity;
import com.home.rw.mvp.ui.activitys.work.GetOutActivity;
import com.home.rw.mvp.ui.activitys.work.ProposeFromMeActivity;
import com.home.rw.mvp.ui.activitys.work.RollMeActivity;
import com.home.rw.mvp.ui.activitys.work.SendRollActivity;
import com.home.rw.mvp.ui.activitys.work.SignInActivity;
import com.home.rw.mvp.ui.activitys.work.SignListActivity;
import com.home.rw.mvp.ui.activitys.work.WipedActivity;
import com.home.rw.mvp.ui.activitys.work.WriteLogActivity;

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
    void inject(OrderActivity orderActivity);
    void inject(WalletActivity walletActivity);
    void inject(CompanyNoticeActivity companyNoticeActivity);
    void inject(WriteLogActivity writeLogActivity);
    void inject(SendRollActivity sendRollActivity);
    void inject(SignListActivity signListActivity);
    void inject(SignInActivity signInActivity);
    void inject(CardActivity cardActivity);
    void inject(FeedBackActivity feedBackActivity);
    void inject(ChangePassWord changePassWord);


}