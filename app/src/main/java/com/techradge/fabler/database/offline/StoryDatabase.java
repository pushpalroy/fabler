package com.techradge.fabler.database.offline;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.techradge.fabler.model.Story;

@Database(entities = {Story.class}, version = 1, exportSchema = false)
public abstract class StoryDatabase extends RoomDatabase {

    private static final String TAG = StoryDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DB_NAME = "storyDatabase";
    private static volatile StoryDatabase sInstance;

    // Returning database instance using Singleton pattern
    public static synchronized StoryDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = createDatabase(context);
            }
        }
        return sInstance;
    }

    private static StoryDatabase createDatabase(final Context context) {
        return Room.databaseBuilder(
                context,
                StoryDatabase.class,
                DB_NAME).build();
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
