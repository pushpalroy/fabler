package com.techradge.fabler.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.techradge.fabler.R;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BaseFragment;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.read.ReadActivity;
import com.techradge.fabler.ui.story.StoryAdapter;
import com.techradge.fabler.ui.story.StoryClickListener;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements HomeContract.HomeView, StoryClickListener {

    @BindView(R.id.story_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.loader)
    NewtonCradleLoading customLoader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;

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
        mPresenter.setUpRemoteDatabase(getResources().getString(R.string.child_story));
        mPresenter.onViewPrepared();
    }

    private void setRecyclerView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mStoryAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showAllStories(List<Story> storyList) {
        mStoryAdapter.flushAndAddItems(storyList);
    }

    @Override
    public void showSingleStory(Story story) {
        mStoryAdapter.addItem(story);
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
    public void openReadActivity(Story story) {
        Intent readIntent = new Intent(getActivity(), ReadActivity.class);
        readIntent.putExtra("title", story.getStoryTitle());
        readIntent.putExtra("story", story.getStoryBody());
        readIntent.putExtra("author", story.getAuthorName());
        readIntent.putExtra("time", story.getPublishedOn());
        startActivity(readIntent);
    }

    @OnClick(R.id.fab)
    public void openComposeActivity() {
        startActivity(ComposeActivity.getStartIntent(getActivity()));
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