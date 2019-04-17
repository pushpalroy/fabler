package com.techradge.fabler.ui.draft;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BaseFragment;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.story.StoryClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DraftFragment extends BaseFragment implements DraftContract.DraftView, StoryClickListener {
    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.drafts_recycler_view)
    public RecyclerView mRecyclerView;
    private final String TAG = DraftFragment.class.getSimpleName();
    private MainViewModel mMainViewModel;

    @Inject
    DraftPresenter<DraftContract.DraftView, DraftContract.DraftInteractor> mPresenter;

    @Inject
    DraftAdapter mDraftAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    public static DraftFragment newInstance() {
        Bundle args = new Bundle();
        DraftFragment fragment = new DraftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft, container, false);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (getActivityComponent() != null) {
            getActivityComponent().inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mDraftAdapter.setCallback(this);
            mDraftAdapter.setMainViewModel(mMainViewModel);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        setRecyclerView();
        mPresenter.onViewPrepared(mMainViewModel, getActivity());
    }

    private void setRecyclerView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mDraftAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void showAllDrafts(List<Story> drafts) {
        mDraftAdapter.flushAndAddItems(drafts);
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
    public void onStoryClick(int position, final Story story) {
        openComposeActivity(story);
        mMainViewModel.deleteStory(story);
    }

    @Override
    public void openComposeActivity(Story story) {
        Intent readIntent = new Intent(getActivity(), ComposeActivity.class);
        if (story != null) {
            readIntent.putExtra("title", story.getTitle());
            readIntent.putExtra("story", story.getStory());
        }

        startActivity(readIntent);
    }

    @OnClick(R.id.fab)
    public void openComposeActivity() {
        startActivity(new Intent(getActivity(), ComposeActivity.class));
    }
}