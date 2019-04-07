package com.techradge.fabler.ui.base;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface MvpPresenter<V extends MvpView, I extends MvpInteractor> {

    void onAttach(V mvpView);

    void onDetach();

    V getMvpView();

    I getInteractor();

    boolean isViewAttached();

    //void checkViewAttached() throws BasePresenter.MvpViewNotAttachedException;

    //void handleApiError(ANError error);

    void setUserAsLoggedOut();
}
