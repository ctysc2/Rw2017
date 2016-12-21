package com.home.rw.di.component;


import android.content.Context;


import com.home.rw.di.module.ServiceModule;
import com.home.rw.di.scope.ContextLife;
import com.home.rw.di.scope.PerService;

import dagger.Component;


@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
