package com.home.rw.di.component;



import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;


import com.home.rw.di.module.FragmentModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerFragment;
import com.home.rw.mvp.ui.fragments.IncrementFragment;
import com.home.rw.mvp.ui.fragments.MessageFragment;
import com.home.rw.mvp.ui.fragments.MineMeFragment;
import com.home.rw.mvp.ui.fragments.SocialFragment;
import com.home.rw.mvp.ui.fragments.WorkFragment;
import com.home.rw.mvp.ui.fragments.mine.AllOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.CompanyWalletFragment;
import com.home.rw.mvp.ui.fragments.mine.LastMonthOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.LastWeekOrderFragment;
import com.home.rw.mvp.ui.fragments.mine.PersonerWalletFragment;
import com.home.rw.mvp.ui.fragments.social.FindFragment;
import com.home.rw.mvp.ui.fragments.social.FocusFragment;
import com.home.rw.mvp.ui.fragments.social.HomePageFragment;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeAfterFragment;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeBeforeFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeApprovingFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMePassedFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeUnPassedFragment;
import com.home.rw.mvp.ui.fragments.work.ReceivedLogFragment;
import com.home.rw.mvp.ui.fragments.work.SendLogFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    FragmentActivity getFragmentActivity();

    void inject(IncrementFragment incrementFragment);
    void inject(MessageFragment messageFragment);
    void inject(MineMeFragment mineMeFragment);
    void inject(SocialFragment socialFragment);
    void inject(WorkFragment workFragment);

    //work
    void inject(ProposeFromMePassedFragment passedFragment);
    void inject(ProposeFromMeUnPassedFragment unPassedFragment);
    void inject(ProposeFromMeApprovingFragment approvingFragment);
    void inject(ApproveByMeBeforeFragment approvedBeforeFragment);
    void inject(ApproveByMeAfterFragment approvedAfterFragment);
    void inject(ReceivedLogFragment receivedLogFragment);
    void inject(SendLogFragment sendLogFragment);

    //mine
    void inject(AllOrderFragment allOrderFragment);
    void inject(LastWeekOrderFragment lastWeekOrderFragment);
    void inject(LastMonthOrderFragment lastMonthOrderFragment);
    void inject(PersonerWalletFragment personerWalletFragment);
    void inject(CompanyWalletFragment companyWalletFragment);

    //social
    void inject(HomePageFragment homePageFragment);
    void inject(FindFragment findFragment);
    void inject(FocusFragment focusFragment);

}
