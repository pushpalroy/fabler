package com.techradge.fabler.ui.home;

import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter<V extends HomeContract.HomeView, I extends HomeContract.HomeInteractor>
        extends BasePresenter<V, I>
        implements HomeContract.HomePresenter<V, I> {

    @Inject
    HomePresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                  CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showCustomLoader();
    }

    @Override
    public void setUpRemoteDatabase(String story) {
        getInteractor().setUpFirebaseDatabase(story);
    }

    @Override
    public void onStoriesPrepare() {
    }

    @Override
    public void onStoriesFetched(List<Story> storyList) {
        getMvpView().showAllStories(storyList);
        getMvpView().hideCustomLoader();
    }

    @Override
    public void onSingleStoryFetched(Story story) {
        getMvpView().showSingleStory(story);
        getMvpView().hideCustomLoader();
    }

    @Override
    public void onDetach() {
        getInteractor().removeListeners();
        super.onDetach();
    }
}
