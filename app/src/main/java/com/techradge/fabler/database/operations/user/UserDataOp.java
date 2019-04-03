package com.techradge.fabler.database.operations.user;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.R;
import com.techradge.fabler.database.offline.AppExecutors;
import com.techradge.fabler.database.offline.UserDatabase;
import com.techradge.fabler.model.User;

public class UserDataOp implements UserData {

    private DatabaseReference usersDatabase;
    private final String TAG = UserDataOp.class.getSimpleName();

    public UserDataOp(FirebaseDatabase firebaseDatabase, Context context) {
        usersDatabase = firebaseDatabase.getReference().child(context.getString(R.string.child_users));
    }

    @Override
    public void insertUserData(final User user, final Context context) {
        try {
            usersDatabase.child(user.getUid()).setValue(user);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        UserDatabase.getInstance(context)
                                .userDao()
                                .insertUser(user);
                    } catch (SQLiteConstraintException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}