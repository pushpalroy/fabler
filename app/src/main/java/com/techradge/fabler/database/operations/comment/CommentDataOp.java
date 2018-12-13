package com.techradge.fabler.database.operations.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.model.Comment;
import com.techradge.fabler.model.Story;

import java.util.HashMap;
import java.util.Map;

public class CommentDataOp {

    private DatabaseReference commentDatabase;
    private final String TAG = CommentDataOp.class.getSimpleName();

    public CommentDataOp(FirebaseDatabase firebaseDatabase, String storyId) {
        commentDatabase = firebaseDatabase.getReference().child("comment").child(storyId);
    }

    public void insertComment(final Comment comment, final Context context) {
        try {
            String key = commentDatabase.push().getKey();
            if (key != null) {
                comment.setCommentId(key);
                commentDatabase.child(key)
                        .setValue(comment)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Comment has been posted!", Toast.LENGTH_LONG).show();
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
}