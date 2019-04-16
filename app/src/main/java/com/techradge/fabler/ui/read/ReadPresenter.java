package com.techradge.fabler.ui.read;

import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ReadPresenter<V extends ReadContract.ReadView, I extends ReadContract.ReadInteractor>
        extends BasePresenter<V, I>
        implements ReadContract.ReadPresenter<V, I> {

    @Inject
    ReadPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                  CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void addStoryToWidget(String title, String author, String body) {
        getInteractor().addToWidget(title, author, body);
    }

    @Override
    public void onWidgetDataReady() {
        getMvpView().sendUpdateWidgetBroadcast();
    }

    @Override
    public void onStoryAddedToWidget() {
        getMvpView().showWidgetAddedMessage();
    }
}
