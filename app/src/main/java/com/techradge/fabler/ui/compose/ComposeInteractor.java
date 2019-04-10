package com.techradge.fabler.ui.compose;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.offline.StoryDatabase;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

import timber.log.Timber;

public class ComposeInteractor extends BaseInteractor implements ComposeContract.ComposeInteractor {

    private final String TAG = ComposeInteractor.class.getSimpleName();

    private Context context;
    private ComposeContract.ComposePresenter mPresenter;

    @Inject
    ComposeInteractor(PreferencesHelper preferencesHelper, @ApplicationContext Context context) {
        super(preferencesHelper);
        this.context = context;
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
                StoryDatabase.getInstance(context)
                        .storyDao()
                        .insertStory(params[0]);
            } catch (SQLiteConstraintException e) {
                Timber.e(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            mPresenter.onSavedLocally();
        }
    }

    @Override
    public void setPresenter(ComposeContract.ComposePresenter composePresenter) {
        mPresenter = composePresenter;
    }
}
