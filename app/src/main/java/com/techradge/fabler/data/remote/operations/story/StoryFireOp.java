package com.techradge.fabler.data.remote.operations.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.model.StoryData;
import com.techradge.fabler.di.ApplicationContext;

import timber.log.Timber;

public class StoryFireOp {

    private DatabaseReference storyRef, storyDataRef;
    private String TAG = "StoryFireOp";
    private Context context;

    public StoryFireOp(FirebaseDatabase firebaseDatabase, @ApplicationContext Context context) {
        storyRef = firebaseDatabase.getReference().child(context.getString(R.string.child_story));
        storyDataRef = firebaseDatabase.getReference().child(context.getString(R.string.child_story_data));
        this.context = context;
    }

    public void insertStory(Story story, StoryData storyData) {
        String key = storyRef.push().getKey();
        try {
            if (story != null && storyData != null && key != null) {
                story.setStoryId(key);
                storyData.setStoryId(key);

                insertStoryData(storyData);
                storyRef
                        .child(key)
                        .setValue(story)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(context,
                                        context.getString(R.string.toast_message_story),
                                        Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> {
                            Timber.e(e.toString());
                        });
            }
        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }

    private void insertStoryData(StoryData storyData) {
        try {
            if (storyData.getStoryId() != null) {
                storyDataRef
                        .child(storyData.getStoryId())
                        .setValue(storyData)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(context,
                                        context.getString(R.string.toast_message_story),
                                        Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> {
                            Timber.e(e.toString());
                        });
            }
        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }

    public void postLikeUpdateStory(Story story) {
    }

    public void postFeedbackUpdateStory(String storyId, String comments) {
        DatabaseReference databaseReference = storyRef.child(storyId).child("comments");
        int commentCount;
        if (comments == null)
            commentCount = 0;
        else
            commentCount = Integer.parseInt(comments);

        commentCount++;

        databaseReference.setValue(String.valueOf(commentCount))
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
