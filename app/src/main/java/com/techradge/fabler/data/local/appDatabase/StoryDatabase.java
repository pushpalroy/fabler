package com.techradge.fabler.data.local.appDatabase;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.techradge.fabler.data.local.dao.StoryDao;
import com.techradge.fabler.data.model.Story;

import static com.techradge.fabler.utils.AppConstants.DB_NAME_STORY;

@Database(entities = {Story.class}, version = 1, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static final String TAG = StoryDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();

    private static volatile StoryDatabase sInstance;

    // Singleton
    public static synchronized StoryDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = createDatabase(context);
            }
        }
        return sInstance;
    }

    private static StoryDatabase createDatabase(final Context context) {
        return Room.databaseBuilder(
                context,
                StoryDatabase.class,
                DB_NAME_STORY).build();
    }

    public abstract StoryDao storyDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
