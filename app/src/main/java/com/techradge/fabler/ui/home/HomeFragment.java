package com.techradge.fabler.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BaseFragment;
import com.techradge.fabler.ui.read.ReadActivity;
import com.techradge.fabler.ui.story.StoryAdapter;
import com.techradge.fabler.ui.story.StoryClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeContract.HomeView, StoryClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.story_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout mSwipeRefresh;
    private boolean mIsLoading = false;

    @Inject
    HomePresenter<HomeContract.HomeView, HomeContract.HomeInteractor> mPresenter;
    @Inject
    StoryAdapter mStoryAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (getActivityComponent() != null) {
            getActivityComponent().inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mStoryAdapter.setCallback(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        setRecyclerView();
        mSwipeRefresh.setOnRefreshListener(this);
        showRefreshLoader();
        mPresenter.fetchStories(getResources().getString(R.string.child_story));
    }

    private void setRecyclerView() {
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mStoryAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                mIsLoading = true;
                mPresenter.fetchMoreStories(mStoryAdapter.getLastStoryId());
                mStoryAdapter.showLoader();
            }

            @Override
            public boolean isLoading() {
                return mSwipeRefresh.isRefreshing() || mIsLoading;
            }
        });
    }

    @Override
    public void showStories(List<Story> storyList) {
        mIsLoading = false;
        hideRefreshLoader();
        mStoryAdapter.removeLoader();
        mStoryAdapter.addItems(storyList);
    }

    @Override
    public void showStory(Story story) {
        mIsLoading = false;
        hideRefreshLoader();
        mStoryAdapter.addSingleItem(story);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onStoryClick(int position, Story story) {
        openReadActivity(story);
    }

    @Override
    public void onBookmarked(int position, Story story) {
        showMessage(getResources().getString(R.string.msg_bookmarked));
    }

    @Override
    public void onBookmarkRemoved(int position, Story story) {
        showMessage(getResources().getString(R.string.msg_bookmark_removed));
    }

    @Override
    public void openReadActivity(Story story) {
        Intent readIntent = new Intent(getActivity(), ReadActivity.class);
        readIntent.putExtra("title", story.getStoryTitle());
        readIntent.putExtra("story", story.getStoryBody());
        readIntent.putExtra("author", story.getAuthorName());
        readIntent.putExtra("time", story.getPublishedOn());
        startActivity(readIntent);
    }

    @Override
    // Show custom loader
    public void showRefreshLoader() {
        if (!mSwipeRefresh.isRefreshing())
            mSwipeRefresh.setRefreshing(true);
    }

    @Override
    // Hide custom loader
    public void hideRefreshLoader() {
        if (mSwipeRefresh.isRefreshing())
            mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mStoryAdapter.clear();
        mPresenter.fetchStories(getResources().getString(R.string.child_story));
    }

    /**
     * Back pressed send from activity.
     *
     * @return if event is consumed, it will return true.
     */
    @Override
    public boolean onBackPressed() {
        if (mLayoutManager.findFirstVisibleItemPosition() != 0) {
            RecyclerView.SmoothScroller smoothScroller =
                    new LinearSmoothScroller(getBaseActivity()) {
                        @Override
                        protected int getVerticalSnapPreference() {
                            return LinearSmoothScroller.SNAP_TO_START;
                        }
                    };

            smoothScroller.setTargetPosition(0);
            mLayoutManager.startSmoothScroll(smoothScroller);

            return true;
        } else
            return false;
    }
}