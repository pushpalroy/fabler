package com.techradge.fabler.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.model.Story;
import com.techradge.fabler.ui.activity.ComposeActivity;
import com.techradge.fabler.ui.activity.ReadActivity;
import com.techradge.fabler.ui.adapter.StoryClickListener;
import com.techradge.fabler.ui.adapter.StoryRecyclerViewAdapter;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements StoryClickListener {

    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.story_recycler_view)
    public RecyclerView storyRecyclerView;
    @BindView(R.id.loader)
    NewtonCradleLoading loader;
    @BindView(R.id.loader_container)
    LinearLayout loaderContainer;
    private Unbinder unbinder;
    private DatabaseReference databaseReference;
    private StoryRecyclerViewAdapter mAdapter;
    private List<Story> mStoryList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent composeIntent = new Intent(getActivity(), ComposeActivity.class);
                startActivity(composeIntent);
            }
        });

        startLoader();
        setRecyclerView();

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

    private void setRecyclerView() {
        mStoryList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new StoryRecyclerViewAdapter(mStoryList, getActivity(), this);
        storyRecyclerView.setLayoutManager(linearLayoutManager);
        storyRecyclerView.setAdapter(mAdapter);

        databaseReference = Database.getFirebaseDatabase().getReference().child(getActivity().getResources().getString(R.string.child_story));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllStories(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAllStories(DataSnapshot dataSnapshot) {
        mStoryList.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Story story = singleSnapshot.getValue(Story.class);
            mStoryList.add(story);
        }

        Collections.reverse(mStoryList);
        mAdapter.notifyItemInserted(mStoryList.size() - 1);
        mAdapter.notifyDataSetChanged();
        dismissLoader();
    }

    @Override
    public void onStoryClick(int position, Story story) {
        Intent readIntent = new Intent(getActivity(), ReadActivity.class);
        readIntent.putExtra("title", story.getTitle());
        readIntent.putExtra("story", story.getStory());
        readIntent.putExtra("author", story.getAuthor());
        readIntent.putExtra("time", story.getTime());
        startActivity(readIntent);
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
}