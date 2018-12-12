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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.R;
import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.model.Story;
import com.techradge.fabler.ui.activity.ComposeActivity;
import com.techradge.fabler.ui.adapter.StoryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.story_recycler_view)
    public RecyclerView storyRecyclerView;
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
        mAdapter = new StoryRecyclerViewAdapter(mStoryList, getActivity());
        storyRecyclerView.setLayoutManager(linearLayoutManager);
        storyRecyclerView.setAdapter(mAdapter);

        databaseReference = Database.getFirebaseDatabase().getReference().child("story");

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
            mAdapter.notifyItemInserted(mStoryList.size() - 1);
            mAdapter.notifyDataSetChanged();

            if (story != null) {
                String title = story.getTitle();
                String body = story.getStory();
                String author = story.getAuthor();
                String time = story.getTime();
            }
        }
    }
}