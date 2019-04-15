package com.techradge.fabler.ui.comment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.ui.base.BaseActivity;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity implements CommentContract.CommentView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_comment)
    EditText commentEditor;
    @BindView(R.id.comment_recycler_view)
    RecyclerView commentRecyclerView;
    @BindView(R.id.loader)
    NewtonCradleLoading customLoader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;

    @Inject
    public CommentPresenter<CommentContract.CommentView, CommentContract.CommentInteractor> mPresenter;

    private final String TAG = CommentActivity.class.getSimpleName();
    private String storyId, comments;
    private List<Comment> mCommentList;
    private CommentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CommentActivity.this);

        if (getIntent().getExtras() != null) {
            storyId = getIntent().getExtras().getString(getString(R.string.key_story_id));
            comments = getIntent().getExtras().getString(getString(R.string.key_comments));
        }

        setUp();
    }

    @OnClick({R.id.button_send})
    public void onSendButtonClickListener() {
        String commentText = commentEditor.getText().toString();
        mPresenter.onSendButtonClicked(storyId, commentText, comments);
    }

    @Override
    public void setUp() {
        setUpActionBar(mToolbar);
        setUpRecyclerView();
        mPresenter.setUpRemoteDatabase(storyId);
    }

    @Override
    public void setUpRecyclerView() {
        mCommentList = new ArrayList<>();
        mAdapter = new CommentAdapter(mCommentList, this);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showAllComments(List<Comment> commentList) {
        mCommentList.clear();
        mCommentList.addAll(commentList);
        Collections.reverse(mCommentList);
        mAdapter.notifyItemInserted(mCommentList.size() - 1);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void resetCommentEditor() {
        commentEditor.setText("");
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