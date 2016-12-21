package com.home.rw.di.component;



import android.content.Context;


import com.home.rw.di.module.ApplicationModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();

}

