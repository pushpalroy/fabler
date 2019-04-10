package com.techradge.fabler.di.module;

import android.app.Service;

import com.techradge.fabler.service.SyncInteractor;
import com.techradge.fabler.service.SyncMvpInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    SyncMvpInteractor provideSyncMvpInteractor(SyncInteractor interactor) {
        return interactor;
    }
}
