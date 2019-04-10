package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.firebase.Database;
import com.techradge.fabler.data.firebase.operations.story.StoryDataOp;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.ui.compose.ComposeContract.ComposeInteractor;
import com.techradge.fabler.ui.compose.ComposeContract.ComposeView;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ComposePresenter<V extends ComposeView, I extends ComposeInteractor>
        extends BasePresenter<V, I>
        implements ComposeContract.ComposePresenter<V, I> {

    private StoryDataOp storyDataOp;

    @Inject
    ComposePresenter(@ActivityContext Context context, I mvpInteractor,
                     SchedulerProvider schedulerProvider,
                     CompositeDisposable compositeDisposable) {

        super(mvpInteractor, schedulerProvider, compositeDisposable);

        storyDataOp = new StoryDataOp(Database.getFirebaseDatabase(), context);

        getInteractor().setPresenter(this);
    }

    @Override
    public void onSaveOptionSelected(Story story) {
        // Locally
        getInteractor().insertStoryInLocalDb(story);
    }

    @Override
    public void onPublishOptionSelected(Story story) {
        // Firebase
        storyDataOp.insertStoryData(story);
    }

    @Override
    public void onSavedLocally() {
        getMvpView().showMessageDraftSaved();
    }

    @Override
    public void onPublished() {

    }
}
