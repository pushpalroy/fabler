package com.techradge.fabler.ui.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.R;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.comment.CommentFireOp;
import com.techradge.fabler.data.remote.operations.story.StoryFireOp;
import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.data.prefs.AppPreferencesHelper;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_comment)
    EditText commentEditor;
    @BindView(R.id.icon_send)
    ImageView sendButton;
    @BindView(R.id.comment_recycler_view)
    RecyclerView commentRecyclerView;
    @BindView(R.id.loader)
    NewtonCradleLoading loader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;
    private CommentFireOp commentFireOp;
    private final String TAG = CommentActivity.class.getSimpleName();
    private String storyId, comments;
    private List<Comment> mCommentList;
    private CommentAdapter mAdapter;
    private DatabaseReference commentDatabaseReference;

    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ButterKnife.bind(this);
        setUpActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storyId = extras.getString(getString(R.string.key_story_id));
            comments = extras.getString(getString(R.string.key_comments));
        }

        commentFireOp = new CommentFireOp(RemoteFireDatabase.getFirebaseDatabase(), storyId, this);
        setRecyclerView();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferencesHelper appPrefsManager = new AppPreferencesHelper(CommentActivity.this);
                String commentText = commentEditor.getText().toString();
                Comment comment = new Comment(commentText, appPrefsManager.getUserFullName(), storyId);
                commentFireOp.insertComment(comment, CommentActivity.this);

                StoryFireOp storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), CommentActivity.this);
                storyFireOp.postCommentUpdateStory(storyId, comments);

                commentEditor.setText("");
            }
        });
    }

    private void setRecyclerView() {
        mCommentList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CommentAdapter(mCommentList, this);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setAdapter(mAdapter);

        commentDatabaseReference = RemoteFireDatabase.getFirebaseDatabase().getReference().child(getString(R.string.child_comment)).child(storyId);

        commentDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                startLoader();
                getAllComments(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllComments(DataSnapshot dataSnapshot) {
        mCommentList.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Comment comment = singleSnapshot.getValue(Comment.class);
            mCommentList.add(comment);
        }

        Collections.reverse(mCommentList);
        mAdapter.notifyItemInserted(mCommentList.size() - 1);
        mAdapter.notifyDataSetChanged();
        dismissLoader();
    }


    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Start loader
    private void startLoader() {
        if (loader != null) {
            loader.setLoadingColor(R.color.colorAccent);
            loaderContainer.setVisibility(View.VISIBLE);
            loader.start();
        }
    }

    // Dismiss loader
    private void dismissLoader() {
        if (loader != null && loader.isStart()) {
            loaderContainer.setVisibility(View.INVISIBLE);
            loader.stop();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}