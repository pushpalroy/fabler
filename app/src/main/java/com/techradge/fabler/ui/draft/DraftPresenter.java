package com.techradge.fabler.ui.draft;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.fragment.app.FragmentActivity;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class DraftPresenter<V extends DraftContract.DraftView, I extends DraftContract.DraftInteractor>
        extends BasePresenter<V, I>
        implements DraftContract.DraftPresenter<V, I> {

    private MainViewModel mMainViewModel;
    private Observer<List<Story>> observer;

    @Inject
    DraftPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                   CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void onViewPrepared(MainViewModel mainViewModel, FragmentActivity fragmentActivity) {
        fetchDrafts(mainViewModel, fragmentActivity);
    }

    private void fetchDrafts(MainViewModel mainViewModel, FragmentActivity fragmentActivity) {
        mMainViewModel = mainViewModel;

        observer = drafts -> {
            if (drafts != null) {
                getMvpView().showAllDrafts(drafts);
            }
        };

        mMainViewModel.getAllStories().observe(fragmentActivity, observer);
    }

    @Override
    public void removeListeners(LifecycleOwner lifecycleOwner) {
        mMainViewModel.getAllStories().removeObservers(lifecycleOwner);
    }
}