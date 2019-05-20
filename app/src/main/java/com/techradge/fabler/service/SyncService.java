package com.techradge.fabler.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.techradge.fabler.MvpApp;
import com.techradge.fabler.di.component.DaggerServiceComponent;
import com.techradge.fabler.di.component.ServiceComponent;
import com.techradge.fabler.di.module.ServiceModule;
import com.techradge.fabler.utils.AppLogger;

import javax.inject.Inject;


public class SyncService extends Service {

    private static final String TAG = "SyncService";

    @Inject
    SyncInteractor mInteractor;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SyncService.class);
        context.startService(starter);
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, SyncService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceComponent component = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(this))
                .applicationComponent(((MvpApp) getApplication()).getComponent())
                .build();
        component.inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppLogger.d(TAG, "SyncService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        AppLogger.d(TAG, "SyncService stopped");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
