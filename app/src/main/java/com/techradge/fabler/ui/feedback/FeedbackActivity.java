package com.techradge.fabler.ui.feedback;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Feedback;
import com.techradge.fabler.ui.base.BaseActivity;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity implements FeedbackContract.FeedbackView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_feedback)
    EditText feedbackEditor;
    @BindView(R.id.feedback_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loader)
    NewtonCradleLoading customLoader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;

    @Inject
    public FeedbackAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    public FeedbackPresenter<FeedbackContract.FeedbackView, FeedbackContract.FeedbackInteractor> mPresenter;

    private final String TAG = FeedbackActivity.class.getSimpleName();
    private String storyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        if (getIntent().getExtras() != null) {
            storyId = getIntent().getExtras().getString(getString(R.string.key_story_id));
        }

        setUp();
    }

    @OnClick({R.id.button_send})
    public void onSendButtonClickListener() {
        String feedbackText = feedbackEditor.getText().toString();
        mPresenter.onSendButtonClicked(storyId, feedbackText);
    }

    @Override
    public void setUp() {
        mPresenter.onAttach(FeedbackActivity.this);
        setUpActionBar(mToolbar);
        setUpRecyclerView();
        mPresenter.setUpRemoteDatabase(storyId);
    }

    @Override
    public void setUpRecyclerView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showAllFeedbacks(List<Feedback> feedbackList) {
        mAdapter.flushAndAddItems(feedbackList);
    }

    @Override
    public void resetFeedbackEditor() {
        feedbackEditor.setText("");
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

    @Override
    // Show custom loader
    public void showCustomLoader() {
        if (customLoader != null) {
            customLoader.setLoadingColor(R.color.colorAccent);
            loaderContainer.setVisibility(View.VISIBLE);
            customLoader.start();
        }
    }

    @Override
    // Hide custom loader
    public void hideCustomLoader() {
        if (customLoader != null && customLoader.isStart()) {
            loaderContainer.setVisibility(View.INVISIBLE);
            customLoader.stop();
        }
    }
}