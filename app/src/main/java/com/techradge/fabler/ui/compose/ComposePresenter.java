package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.firebase.Database;
import com.techradge.fabler.data.firebase.operations.story.StoryDataOp;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.offline.StoryDatabase;
import com.techradge.fabler.data.prefs.AppPrefsManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class ComposePresenter implements ComposeContract.ComposePresenter {

    private StoryDataOp storyDataOp;
    private AppPrefsManager appPrefsManager;
    private ComposeContract.ComposeView mView;
    private ComposeContract.ComposeInteractor mInteractor;

    ComposePresenter(ComposeContract.ComposeView composeView) {
        mView = checkNotNull(composeView, "ComposeView cannot be null.");
        storyDataOp = new StoryDataOp(Database.getFirebaseDatabase(), mView.getContext());
        appPrefsManager = new AppPrefsManager(mView.getContext());
        mInteractor = new ComposeInteractor(this, StoryDatabase.getInstance(mView.getContext()));
    }

    @Override
    public void onSaveOptionSelected(Story story) {
        mInteractor.insertStoryInLocalDb(story);
    }

    @Override
    public void onPublishOptionSelected(Story story, Context context) {
        storyDataOp.insertStoryData(story, context);
    }

    @Override
    public void onStorySavedLocally() {
        mView.showDraftSavedMessage();
    }

    @Override
    public void start() {

    }
}
