package com.techradge.fabler.ui.comment;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.comment.CommentFireOp;
import com.techradge.fabler.data.remote.operations.story.StoryFireOp;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class CommentInteractor extends BaseInteractor implements CommentContract.CommentInteractor {

    private PreferencesHelper mPreferencesHelper;
    private CommentContract.CommentPresenter mPresenter;
    private Context mContext;

    @Inject
    public CommentInteractor(@ActivityContext Context context, PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
        mPreferencesHelper = preferencesHelper;
        mContext = context;
    }

    @Override
    public void insertCommentFirebase(String storyId, String commentBody) {
        CommentFireOp commentFireOp = new CommentFireOp(RemoteFireDatabase.getFirebaseDatabase(), storyId, mContext);
        Comment comment = new Comment(storyId,
                mPreferencesHelper.getUserFullName(),
                commentBody,
                Calendar.getInstance().getTimeInMillis());
        commentFireOp.insertComment(comment, mContext);
    }

    @Override
    public void updateStoryFirebase(String storyId, String comments) {
        StoryFireOp storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), mContext);
        storyFireOp.postCommentUpdateStory(storyId, comments);
    }

    @Override
    public void setUpFirebaseDatabase(String storyId) {
        DatabaseReference commentDatabaseReference = RemoteFireDatabase
                .getFirebaseDatabase()
                .getReference()
                .child(mContext.getString(R.string.child_comment))
                .child(storyId);
        commentDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPresenter.onNewCommentPrepare();
                List<Comment> commentList =
                        fetchCommentsFromDataSnapshot(dataSnapshot);
                mPresenter.onNewCommentListFetched(commentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private List<Comment> fetchCommentsFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Comment> commentList = new ArrayList<>();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Comment comment = singleSnapshot.getValue(Comment.class);
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public void setPresenter(CommentContract.CommentPresenter commentPresenter) {
        mPresenter = commentPresenter;
    }
}
