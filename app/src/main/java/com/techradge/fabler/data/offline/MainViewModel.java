package com.techradge.fabler.data.offline;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.techradge.fabler.data.model.Story;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private final LiveData<List<Story>> drafts;

    public MainViewModel(@NonNull Application application) {
        super(application);

        StoryDatabase storyDatabase = StoryDatabase.getInstance(this.getApplication());
        Log.e(TAG, "Actively retrieving the drafts from the database");
        drafts = storyDatabase.storyDao().getStories();
    }

    public LiveData<List<Story>> getFavMovies() {
        return drafts;
    }
}