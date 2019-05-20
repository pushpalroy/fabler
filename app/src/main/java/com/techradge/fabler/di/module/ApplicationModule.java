package com.techradge.fabler.di.module;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;
import androidx.annotation.NonNull;

import com.techradge.fabler.BuildConfig;
import com.techradge.fabler.data.local.appDatabase.StoryDatabase;
import com.techradge.fabler.data.local.appDatabase.UserDatabase;
import com.techradge.fabler.data.prefs.AppPreferencesHelper;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.di.ApiInfo;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.di.DatabaseInfo;
import com.techradge.fabler.di.PreferenceInfo;
import com.techradge.fabler.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.techradge.fabler.utils.AppConstants.DB_NAME_STORY;
import static com.techradge.fabler.utils.AppConstants.DB_NAME_USER;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    StoryDatabase provideStoryDatabase(@NonNull Application application) {
        return Room.databaseBuilder(
                application,
                StoryDatabase.class,
                DB_NAME_STORY).build();
    }

    @Provides
    @DatabaseInfo
    String provideStoryDatabaseName() {
        return AppConstants.DB_NAME_STORY;
    }

    @Provides
    @Singleton
    UserDatabase provideUserDatabase(@NonNull Application application) {
        return Room.databaseBuilder(
                application,
                UserDatabase.class,
                DB_NAME_USER).build();
    }

    @Provides
    @DatabaseInfo
    String provideUserDatabaseName() {
        return AppConstants.DB_NAME_USER;
    }
}