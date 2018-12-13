package com.techradge.fabler.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.database.operations.comment.CommentDataOp;
import com.techradge.fabler.model.Comment;
import com.techradge.fabler.utils.PrefManager;

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

    private CommentDataOp commentDataOp;
    private final String TAG = CommentActivity.class.getSimpleName();
    private String storyId;

    public CommentActivity() {
        commentDataOp = new CommentDataOp(Database.getFirebaseDatabase());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ButterKnife.bind(this);
        setUpActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            storyId = extras.getString("storyId");
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(CommentActivity.this);
                String commentText = commentEditor.getText().toString();
                Comment comment = new Comment(commentText, prefManager.getUserFullName(), storyId);
                commentDataOp.insertComment(comment, CommentActivity.this);
                commentEditor.setText("");
            }
        });
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
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