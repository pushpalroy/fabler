package com.techradge.fabler.data.local.appDatabase;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.techradge.fabler.data.local.dao.UserDao;
import com.techradge.fabler.data.model.User;

import static com.techradge.fabler.utils.AppConstants.DB_NAME_USER;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String TAG = UserDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static volatile UserDatabase sInstance;

    // Returning database instance using Singleton pattern
    public static synchronized UserDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = createDatabase(context);
            }
        }
        return sInstance;
    }

    private static UserDatabase createDatabase(final Context context) {
        return Room.databaseBuilder(
                context,
                UserDatabase.class,
                DB_NAME_USER).build();
    }

    public abstract UserDao userDao();

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
