package com.techradge.fabler.ui.read;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

public class ReadInteractor extends BaseInteractor implements ReadContract.ReadInteractor {

    public ReadContract.ReadPresenter mPresenter;
    private PreferencesHelper mPreferencesHelper;

    @Inject
    public ReadInteractor(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void addToWidget(String title, String author, String body) {
        new AddToWidget().execute(title, author, body);
    }

    @SuppressLint("StaticFieldLeak")
    private class AddToWidget extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            mPreferencesHelper.setWidgetStoryTitle(params[0]);
            mPreferencesHelper.setWidgetStoryAuthor(params[1]);
            mPreferencesHelper.setWidgetStoryBody(params[2]);
            mPresenter.onWidgetDataReady();
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            mPresenter.onStoryAddedToWidget();
        }
    }

    @Override
    public void setPresenter(ReadContract.ReadPresenter presenter) {
        mPresenter = presenter;
    }
}
