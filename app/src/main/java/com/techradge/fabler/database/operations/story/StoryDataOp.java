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

import java.util.HashMap;
import java.util.Map;

public class StoryDataOp {

    private DatabaseReference storyDatabase;
    private String TAG = "StoryDataOp";

    public StoryDataOp(FirebaseDatabase firebaseDatabase) {
        storyDatabase = firebaseDatabase.getReference().child("story");
    }

    public void insertStoryData(Story story, final Context context) {
        String key = storyDatabase.push().getKey();
        try {
            if (key != null) {
                story.setStoryId(key);
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
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void postLikeUpdateStory(Story story) {
        Map<String, Object> postValues = story.toMap();
        if (story.getLikes() == null)
            story.setLikes("0");
        int likes = Integer.parseInt(story.getLikes()) + 1;
        postValues.put("likes", String.valueOf(likes));

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(story.getStoryId(), postValues);

        storyDatabase.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
}
