package com.techradge.fabler.data.remote.operations.comment;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Comment;

import timber.log.Timber;

public class CommentFireOp {

    private DatabaseReference commentDatabase;
    private final String TAG = CommentFireOp.class.getSimpleName();

    public CommentFireOp(FirebaseDatabase firebaseDatabase, String storyId, Context context) {
        commentDatabase = firebaseDatabase.getReference().child(context.getString(R.string.child_comment)).child(storyId);
    }

    public void insertComment(final Comment comment, final Context context) {
        try {
            String key = commentDatabase.push().getKey();
            if (key != null) {
                comment.setCommentId(key);
                commentDatabase.child(key)
                        .setValue(comment)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(context,
                                        context.getString(R.string.toast_message_comment),
                                        Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> {
                        });
            }
        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }
}