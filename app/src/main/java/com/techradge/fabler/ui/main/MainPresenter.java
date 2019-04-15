package com.techradge.fabler.ui.main;

import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainContract.MainView, I extends MainContract.MainInteractor>
        extends BasePresenter<V, I>
        implements MainContract.MainPresenter<V, I> {

    @Inject
    MainPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                  CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void loadUserDetails() {
        getInteractor().fetchUserDetails();
    }

    @Override
    public void onUserDataLoaded(String userFullName, String userEmail, String userPhotoUrl) {
        getMvpView().populateUserDetails(userFullName, userEmail, userPhotoUrl);
    }

    @Override
    public void setUserLoggedOut() {
        getInteractor().setUserAsLoggedOut();
    }
}
