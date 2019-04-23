package com.techradge.fabler.ui.login;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.ui.login.LoginContract.LoginInteractor;
import com.techradge.fabler.ui.login.LoginContract.LoginView;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter<V extends LoginView, I extends LoginInteractor>
        extends BasePresenter<V, I>
        implements LoginContract.LoginPresenter<V, I> {

    private static final String TAG = "LoginPresenter";

    private MainViewModel mMainViewModel;

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
        getInteractor().insertUserDataLocal(user, mMainViewModel);

        user.setProfileCreatedOn(Calendar.getInstance().getTimeInMillis());
        getInteractor().insertUserDataRemote(user);

        onUserDataInserted(user);
    }

    @Override
    public void onUserDataInserted(User user) {
        getMvpView().openWelcomeActivity(user);
    }

    @Override
    public void setViewModel(MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
    }
}