package com.home.rw.mvp.presenter.impl;

import com.home.rw.mvp.entity.MainPageEntity;
import com.home.rw.mvp.entity.MixFocusEntity;
import com.home.rw.mvp.interactor.MainPageInteractor;
import com.home.rw.mvp.interactor.MixFocusInteractor;
import com.home.rw.mvp.interactor.impl.MainPageInteractorImpl;
import com.home.rw.mvp.interactor.impl.MixFocusInteractorImpl;
import com.home.rw.mvp.presenter.base.BasePresenterImpl;
import com.home.rw.mvp.view.MainPageView;

import javax.inject.Inject;

/**
 * Created by cty on 2017/3/12.
 */

public class MainPagePresenterImpl extends BasePresenterImpl<MainPageView,MainPageEntity>{

    private MainPageInteractor mMainPageInteractorImpl;

    @Inject
    public MainPagePresenterImpl(MainPageInteractorImpl mainPageInteractor){
        mMainPageInteractorImpl = mainPageInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void getMainPage(){
        mSubscription = mMainPageInteractorImpl.getMainPage(this);

    }
    @Override
    public void success(MainPageEntity data) {
        super.success(data);
        mView.getMainPageCompleted(data);
    }

}
