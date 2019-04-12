package com.techradge.fabler.ui.draft;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techradge.fabler.R;
import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.compose.ComposeActivity;
import com.techradge.fabler.ui.story.StoryClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DraftFragment extends Fragment implements StoryClickListener {
    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.drafts_recycler_view)
    public RecyclerView draftsRecyclerView;
    private Unbinder unbinder;
    private DraftAdapter mAdapter;
    private List<Story> draftsList;
    private final String TAG = DraftFragment.class.getSimpleName();
    private MainViewModel mMainViewModel;

    public DraftFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_draft, container, false);

        unbinder = ButterKnife.bind(this, rootView);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        listenerSetup();
        observerSetup();
        recyclerViewSetup();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStoryClick(int position, final Story story) {
        Intent readIntent = new Intent(getActivity(), ComposeActivity.class);
        readIntent.putExtra("title", story.getTitle());
        readIntent.putExtra("story", story.getStory());
        startActivity(readIntent);

        mMainViewModel.deleteStory(story);
    }

    private void recyclerViewSetup() {
        draftsList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new DraftAdapter(draftsList, getActivity(), mMainViewModel, this);
        draftsRecyclerView.setLayoutManager(linearLayoutManager);
        draftsRecyclerView.setAdapter(mAdapter);
    }

    public void observerSetup() {
        mMainViewModel
                .getAllStories()
                .observe(getActivity(),
                        new Observer<List<Story>>() {
                            @Override
                            public void onChanged(@Nullable List<Story> stories) {
                                if (stories != null) {
                                    draftsList.clear();
                                    draftsList.addAll(stories);
                                    Collections.reverse(draftsList);
                                    mAdapter.notifyItemInserted(draftsList.size() - 1);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
    }

    public void listenerSetup() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent composeIntent = new Intent(getActivity(), ComposeActivity.class);
                startActivity(composeIntent);
            }
        });
    }
}