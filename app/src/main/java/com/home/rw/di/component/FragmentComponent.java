package com.home.rw.di.component;



import android.app.Activity;
import android.content.Context;


import com.home.rw.di.module.FragmentModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerFragment;
import com.home.rw.mvp.ui.fragments.IncrementFragment;
import com.home.rw.mvp.ui.fragments.MessageFragment;
import com.home.rw.mvp.ui.fragments.MineMeFragment;
import com.home.rw.mvp.ui.fragments.SocialFragment;
import com.home.rw.mvp.ui.fragments.WorkFragment;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeAfterFragment;
import com.home.rw.mvp.ui.fragments.work.ApproveByMeBeforeFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeApprovingFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMePassedFragment;
import com.home.rw.mvp.ui.fragments.work.ProposeFromMeUnPassedFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

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

}
