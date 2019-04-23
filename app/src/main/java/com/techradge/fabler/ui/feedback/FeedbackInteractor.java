package com.techradge.fabler.ui.feedback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Feedback;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.feedback.FeedbackFireOp;
import com.techradge.fabler.data.remote.operations.story.StoryFireOp;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class FeedbackInteractor extends BaseInteractor implements FeedbackContract.FeedbackInteractor {

    private PreferencesHelper mPreferencesHelper;
    private FeedbackContract.FeedbackPresenter mPresenter;
    private Context mContext;

    @Inject
    public FeedbackInteractor(@ActivityContext Context context, PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
        mPreferencesHelper = preferencesHelper;
        mContext = context;
    }

    @Override
    public void insertFeedbackFirebase(String storyId, String feedbackBody) {
        FeedbackFireOp feedbackFireOp = new FeedbackFireOp(RemoteFireDatabase.getFirebaseDatabase(), storyId, mContext);
        Feedback feedback = new Feedback(storyId,
                mPreferencesHelper.getUserFullName(),
                feedbackBody,
                Calendar.getInstance().getTimeInMillis());
        feedbackFireOp.insertFeedback(feedback, mContext);
    }

    @Override
    public void updateStoryFirebase(String storyId, String feedbacks) {
        StoryFireOp storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), mContext);
        storyFireOp.postFeedbackUpdateStory(storyId, feedbacks);
    }

    @Override
    public void setUpFirebaseDatabase(String storyId) {
        DatabaseReference feedbackDatabaseReference = RemoteFireDatabase
                .getFirebaseDatabase()
                .getReference()
                .child(mContext.getString(R.string.child_feedback))
                .child(storyId);
        feedbackDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPresenter.onNewFeedbackPrepare();
                List<Feedback> feedbackList =
                        fetchFeedbacksFromDataSnapshot(dataSnapshot);
                mPresenter.onNewFeedbackListFetched(feedbackList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private List<Feedback> fetchFeedbacksFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Feedback> feedbackList = new ArrayList<>();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Feedback feedback = singleSnapshot.getValue(Feedback.class);
            feedbackList.add(feedback);
        }
        return feedbackList;
    }

    @Override
    public void setPresenter(FeedbackContract.FeedbackPresenter feedbackPresenter) {
        mPresenter = feedbackPresenter;
    }
}
