package com.techradge.fabler.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.ui.compose.ComposeContract;
import com.techradge.fabler.ui.compose.ComposeContract.ComposeView;
import com.techradge.fabler.ui.compose.ComposeInteractor;
import com.techradge.fabler.ui.compose.ComposePresenter;
import com.techradge.fabler.ui.login.LoginContract;
import com.techradge.fabler.ui.login.LoginContract.LoginView;
import com.techradge.fabler.ui.login.LoginInteractor;
import com.techradge.fabler.ui.login.LoginPresenter;
import com.techradge.fabler.utils.rx.AppSchedulerProvider;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    //  LOGIN   //

    @Provides
    @PerActivity
    LoginContract.LoginPresenter<LoginView, LoginContract.LoginInteractor> provideLoginPresenter(
            LoginPresenter<LoginView, LoginContract.LoginInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginContract.LoginInteractor provideLoginInteractor(LoginInteractor interactor) {
        return interactor;
    }

    //  COMPOSE //

    @Provides
    @PerActivity
    ComposeContract.ComposePresenter<ComposeView, ComposeContract.ComposeInteractor> provideComposePresenter(
            ComposePresenter<ComposeView, ComposeContract.ComposeInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ComposeContract.ComposeInteractor provideComposeInteractor(ComposeInteractor interactor) {
        return interactor;
    }
}
