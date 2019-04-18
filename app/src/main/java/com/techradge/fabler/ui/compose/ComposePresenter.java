package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.story.StoryFireOp;
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

    private StoryFireOp storyFireOp;
    private MainViewModel mMainViewModel;

    @Inject
    ComposePresenter(@ActivityContext Context context, I mvpInteractor,
                     SchedulerProvider schedulerProvider,
                     CompositeDisposable compositeDisposable) {

        super(mvpInteractor, schedulerProvider, compositeDisposable);

        storyFireOp = new StoryFireOp(RemoteFireDatabase.getFirebaseDatabase(), context);

        getInteractor().setPresenter(this);
    }

    @Override
    public void onSaveOptionSelected(Story story) {
        // Locally
        mMainViewModel.insertStory(story);
        getMvpView().showMessageDraftSaved();
    }

    @Override
    public void onModifyOptionSelected(Story story) {
        // Locally
        mMainViewModel.updateStory(story);
        getMvpView().showMessageDraftSaved();
    }

    @Override
    public void onPublishOptionSelected(Story story) {
        // Firebase
        storyFireOp.insertStoryData(story);
    }


    @Override
    public void onPublished() {
    }

    @Override
    public void setViewModel(MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
    }
}
