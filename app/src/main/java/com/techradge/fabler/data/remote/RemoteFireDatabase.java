package com.techradge.fabler.data.remote;

import com.google.firebase.database.FirebaseDatabase;

public class RemoteFireDatabase {
    private static FirebaseDatabase firebaseDatabase = null;

    public static FirebaseDatabase getFirebaseDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return firebaseDatabase;
    }
}
