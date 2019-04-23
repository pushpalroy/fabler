package com.techradge.fabler.data.remote.operations.feedback;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Feedback;

import timber.log.Timber;

public class FeedbackFireOp {

    private DatabaseReference feedbackDatabase;
    private final String TAG = FeedbackFireOp.class.getSimpleName();

    public FeedbackFireOp(FirebaseDatabase firebaseDatabase, String storyId, Context context) {
        feedbackDatabase = firebaseDatabase.getReference().child(context.getString(R.string.child_feedback)).child(storyId);
    }

    public void insertFeedback(final Feedback feedback, final Context context) {
        try {
            String key = feedbackDatabase.push().getKey();
            if (key != null) {
                feedback.setFeedbackId(key);
                feedbackDatabase.child(key)
                        .setValue(feedback)
                        .addOnSuccessListener(aVoid ->
                                Toast.makeText(context,
                                        context.getString(R.string.toast_message_feedback),
                                        Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> {
                        });
            }
        } catch (Exception e) {
            Timber.e(e.toString());
        }
    }
}