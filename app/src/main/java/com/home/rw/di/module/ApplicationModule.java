package com.home.rw.di.module;

import android.content.Context;


import com.home.rw.application.App;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }

}
