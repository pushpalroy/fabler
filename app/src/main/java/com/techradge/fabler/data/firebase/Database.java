package com.techradge.fabler.data.firebase;

import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private static FirebaseDatabase firebaseDatabase = null;

    public static FirebaseDatabase getFirebaseDatabase() {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return firebaseDatabase;
    }
}
