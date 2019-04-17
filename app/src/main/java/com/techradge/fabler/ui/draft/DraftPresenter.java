package com.techradge.fabler.ui.draft;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

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

    @Inject
    public DraftPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void onViewPrepared(MainViewModel mainViewModel, FragmentActivity fragmentActivity) {
        fetchDrafts(mainViewModel, fragmentActivity);
    }

    private void fetchDrafts(MainViewModel mainViewModel, FragmentActivity fragmentActivity) {
        mainViewModel
                .getAllStories()
                .observe(fragmentActivity,
                        new Observer<List<Story>>() {
                            @Override
                            public void onChanged(@Nullable List<Story> drafts) {
                                if (drafts != null) {
                                    getMvpView().showAllDrafts(drafts);
                                }
                            }
                        });
    }
}
