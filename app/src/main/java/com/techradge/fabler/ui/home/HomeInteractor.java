package com.techradge.fabler.ui.home;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.ui.base.BaseInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeInteractor extends BaseInteractor implements HomeContract.HomeInteractor {

    private HomeContract.HomePresenter mPresenter;
    private DatabaseReference mStoriesDatabaseReference;
    private ValueEventListener mValueEventListener;

    @Inject
    public HomeInteractor(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    @Override
    public void setUpFirebaseDatabase(String story) {
        mStoriesDatabaseReference = RemoteFireDatabase
                .getFirebaseDatabase()
                .getReference()
                .child(story);

        mValueEventListener = mStoriesDatabaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mPresenter.onStoriesPrepare();
                        List<Story> storyList = fetchStoriesFromDataSnapshot(dataSnapshot);
                        mPresenter.onStoriesFetched(storyList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private List<Story> fetchStoriesFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Story> storyList = new ArrayList<>();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Story story = singleSnapshot.getValue(Story.class);
            storyList.add(story);
        }
        return storyList;
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void removeListeners() {
        mStoriesDatabaseReference.removeEventListener(mValueEventListener);
    }
}
