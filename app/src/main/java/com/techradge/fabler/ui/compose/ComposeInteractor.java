package com.techradge.fabler.ui.compose;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.offline.StoryDatabase;

public class ComposeInteractor implements ComposeContract.ComposeInteractor {

    private final String TAG = ComposeInteractor.class.getSimpleName();
    private ComposePresenter mPresenter;
    private StoryDatabase mDatabase;

    ComposeInteractor(ComposePresenter composePresenter, StoryDatabase storyDatabase) {
        mPresenter = composePresenter;
        mDatabase = storyDatabase;
    }

    @Override
    public void insertStoryInLocalDb(Story story) {
        new SaveStory().execute(story);
    }

    @SuppressLint("StaticFieldLeak")
    class SaveStory extends AsyncTask<Story, Void, Void> {

        @Override
        protected Void doInBackground(Story... params) {
            try {
                mDatabase
                        .storyDao()
                        .insertStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            mPresenter.onStorySavedLocally();
        }
    }
}
