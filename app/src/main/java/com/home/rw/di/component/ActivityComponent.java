package com.home.rw.di.component;

import android.app.Activity;
import android.content.Context;


import com.home.rw.di.module.ActivityModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerActivity;
import com.home.rw.mvp.ui.activitys.LoginActivity;
import com.home.rw.mvp.ui.activitys.MainActivity;
import com.home.rw.mvp.ui.activitys.SplashActivity;
import com.home.rw.mvp.ui.activitys.lock.LockMainActivity;
import com.home.rw.mvp.ui.activitys.message.BusinessPhoneActivity;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeActivity;
import com.home.rw.mvp.ui.activitys.message.CompanyNoticeDetailActivity;
import com.home.rw.mvp.ui.activitys.message.ContactsActivity;
import com.home.rw.mvp.ui.activitys.message.GroupChatActivity;
import com.home.rw.mvp.ui.activitys.message.GroupChatNamedActivity;
import com.home.rw.mvp.ui.activitys.message.GroupChatSelectActivity;
import com.home.rw.mvp.ui.activitys.message.LandMarkDetail;
import com.home.rw.mvp.ui.activitys.message.LandMarkNotice;
import com.home.rw.mvp.ui.activitys.message.MeetingSelectActivity;
import com.home.rw.mvp.ui.activitys.message.ModifiRemarkActivity;
import com.home.rw.mvp.ui.activitys.message.MyFriendActivity;
import com.home.rw.mvp.ui.activitys.message.MyNewFriendActivity;
import com.home.rw.mvp.ui.activitys.message.MyTeamActivity;
import com.home.rw.mvp.ui.activitys.message.OriganizationActivity;
import com.home.rw.mvp.ui.activitys.message.SendFriendVerifiAvtivity;
import com.home.rw.mvp.ui.activitys.mineme.ChangePassWord;
import com.home.rw.mvp.ui.activitys.mineme.FeedBackActivity;
import com.home.rw.mvp.ui.activitys.mineme.OrderActivity;
import com.home.rw.mvp.ui.activitys.mineme.SettingActivity;
import com.home.rw.mvp.ui.activitys.mineme.WalletActivity;
import com.home.rw.mvp.ui.activitys.rongCloud.ConversationActivity;
import com.home.rw.mvp.ui.activitys.rongCloud.ConversationListActivity;
import com.home.rw.mvp.ui.activitys.social.CommDetailActivity;
import com.home.rw.mvp.ui.activitys.social.CommListActivity;
import com.home.rw.mvp.ui.activitys.social.CommPublishActivity;
import com.home.rw.mvp.ui.activitys.social.FocusListActivity;
import com.home.rw.mvp.ui.activitys.social.OthersDetailActivity;
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
    void inject(SettingActivity settingActivity);
    void inject(FocusListActivity focusListActivity);
    void inject(CommPublishActivity commPublishActivity);
    void inject(CommDetailActivity commDetailActivity);
    void inject(OthersDetailActivity othersDetailActivity);
    void inject(CommListActivity commListActivity);
    void inject(LandMarkNotice landMarkNotice);
    void inject(CompanyNoticeDetailActivity companyNoticeDetailActivity);
    void inject(ModifiRemarkActivity modifiRemarkActivity);
    void inject(BusinessPhoneActivity businessPhoneActivity);
    void inject(MyFriendActivity myFriendActivity);
    void inject(GroupChatActivity groupChatActivity);
    void inject(GroupChatNamedActivity groupChatNamedActivity);
    void inject(GroupChatSelectActivity groupChatSelectActivity);
    void inject(MyNewFriendActivity myNewFriendActivity);
    void inject(LandMarkDetail landMarkDetail);
    void inject(OriganizationActivity origanizationActivity);
    void inject(SendFriendVerifiAvtivity sendFriendVerifiAvtivity);
    void inject(ContactsActivity contactsActivity);
    void inject(MyTeamActivity myTeamActivity);
    void inject(ConversationActivity conversationActivity);
    void inject(MeetingSelectActivity conversationActivity);
    void inject(ConversationListActivity conversationListActivity);
    void inject(LockMainActivity lockMainActivity);
    void inject(SplashActivity splashActivity);



}