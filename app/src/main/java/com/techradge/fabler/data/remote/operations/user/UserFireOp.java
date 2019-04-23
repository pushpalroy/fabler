package com.techradge.fabler.data.remote.operations.user;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.User;

import timber.log.Timber;

public class UserFireOp implements UserFirebase {

    private DatabaseReference usersDatabase;
    private final String TAG = UserFireOp.class.getSimpleName();

    public UserFireOp(FirebaseDatabase firebaseDatabase, Context context) {
        usersDatabase = firebaseDatabase.getReference().child(context.getString(R.string.child_users));
    }

    @Override
    public void insertUserData(final User user) {
        try {
            if (user != null) {
                usersDatabase.child(user.getUid()).setValue(user)
                        .addOnSuccessListener(aVoid -> {
                        })
                        .addOnFailureListener(e -> {
                        });
            }

        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }
}