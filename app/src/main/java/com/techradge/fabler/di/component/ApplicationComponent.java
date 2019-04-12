package com.techradge.fabler.di.component;

import android.app.Application;
import android.content.Context;

import com.techradge.fabler.MvpApp;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.di.module.ApplicationModule;
import com.techradge.fabler.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MvpApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();
}