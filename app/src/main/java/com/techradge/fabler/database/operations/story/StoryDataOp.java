package com.techradge.fabler.database.operations.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.model.Story;

public class StoryDataOp {

    private DatabaseReference storyDatabase;
    private String TAG = "StoryDataOp";

    public StoryDataOp(FirebaseDatabase firebaseDatabase) {
        storyDatabase = firebaseDatabase.getReference().child("story");
    }

    public void insertStoryData(Story story, final Context context) {
        String key = storyDatabase.push().getKey();
        try {
            storyDatabase.child(key)
                    .setValue(story)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Story has been published", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
            ;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
