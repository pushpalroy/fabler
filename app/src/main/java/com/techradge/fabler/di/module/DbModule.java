package com.techradge.fabler.di.module;

import android.app.Application;
import androidx.room.Room;
import androidx.annotation.NonNull;

import com.techradge.fabler.data.local.appDatabase.StoryDatabase;
import com.techradge.fabler.data.local.appDatabase.UserDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.techradge.fabler.utils.AppConstants.DB_NAME_STORY;
import static com.techradge.fabler.utils.AppConstants.DB_NAME_USER;

@Module
public class DbModule {

    @Provides
    @Singleton
    StoryDatabase provideStoryDatabase(@NonNull Application application) {
        return Room.databaseBuilder(
                application,
                StoryDatabase.class,
                DB_NAME_STORY).build();
    }

    @Provides
    @Singleton
    UserDatabase provideUserDatabase(@NonNull Application application) {
        return Room.databaseBuilder(
                application,
                UserDatabase.class,
                DB_NAME_USER).build();
    }
}
