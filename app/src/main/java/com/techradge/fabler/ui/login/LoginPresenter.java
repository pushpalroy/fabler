package com.techradge.fabler.ui.login;

import com.techradge.fabler.data.model.User;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.ui.login.LoginContract.LoginInteractor;
import com.techradge.fabler.ui.login.LoginContract.LoginView;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginView, I extends LoginInteractor>
        extends BasePresenter<V, I>
        implements LoginContract.LoginPresenter<V, I> {

    private static final String TAG = "LoginPresenter";


    @Inject
    LoginPresenter(I mvpInteractor,
                   SchedulerProvider schedulerProvider,
                   CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
    }

    @Override
    public void launch() {
        if (getInteractor().isUserLoggedIn())
            getMvpView().openHomeActivity();
        else
            getMvpView().startFirebaseUIAuth();
    }

    @Override
    public void onAuthenticated(User user) {
        String uid = user.getUid();
        getInteractor().insertUserDataLocal(user);
        onUserDataInsertedLocal(user);
    }

    @Override
    public void onUserDataInsertedLocal(User user) {
        getMvpView().openWelcomeActivity(user);
    }
}