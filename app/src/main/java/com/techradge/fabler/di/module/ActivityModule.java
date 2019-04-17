package com.techradge.fabler.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.ui.comment.CommentAdapter;
import com.techradge.fabler.ui.comment.CommentContract;
import com.techradge.fabler.ui.comment.CommentInteractor;
import com.techradge.fabler.ui.comment.CommentPresenter;
import com.techradge.fabler.ui.compose.ComposeContract;
import com.techradge.fabler.ui.compose.ComposeInteractor;
import com.techradge.fabler.ui.compose.ComposePresenter;
import com.techradge.fabler.ui.home.HomeContract;
import com.techradge.fabler.ui.home.HomeInteractor;
import com.techradge.fabler.ui.home.HomePresenter;
import com.techradge.fabler.ui.login.LoginContract;
import com.techradge.fabler.ui.login.LoginInteractor;
import com.techradge.fabler.ui.login.LoginPresenter;
import com.techradge.fabler.ui.main.MainContract;
import com.techradge.fabler.ui.main.MainInteractor;
import com.techradge.fabler.ui.main.MainPresenter;
import com.techradge.fabler.ui.read.ReadContract;
import com.techradge.fabler.ui.read.ReadInteractor;
import com.techradge.fabler.ui.read.ReadPresenter;
import com.techradge.fabler.ui.story.StoryAdapter;
import com.techradge.fabler.utils.rx.AppSchedulerProvider;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.ArrayList;

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
    LoginContract.LoginPresenter<LoginContract.LoginView, LoginContract.LoginInteractor> provideLoginPresenter(
            LoginPresenter<LoginContract.LoginView, LoginContract.LoginInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginContract.LoginInteractor provideLoginInteractor(LoginInteractor interactor) {
        return interactor;
    }

    //  MAIN //

    @Provides
    @PerActivity
    MainContract.MainPresenter<MainContract.MainView, MainContract.MainInteractor> provideMainPresenter(
            MainPresenter<MainContract.MainView, MainContract.MainInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainContract.MainInteractor provideMainInteractor(MainInteractor interactor) {
        return interactor;
    }

    //  COMPOSE //

    @Provides
    @PerActivity
    ComposeContract.ComposePresenter<ComposeContract.ComposeView, ComposeContract.ComposeInteractor> provideComposePresenter(
            ComposePresenter<ComposeContract.ComposeView, ComposeContract.ComposeInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ComposeContract.ComposeInteractor provideComposeInteractor(ComposeInteractor interactor) {
        return interactor;
    }

    //  COMMENT //

    @Provides
    @PerActivity
    CommentContract.CommentPresenter<CommentContract.CommentView, CommentContract.CommentInteractor> provideCommentPresenter(
            CommentPresenter<CommentContract.CommentView, CommentContract.CommentInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CommentContract.CommentInteractor provideCommentInteractor(CommentInteractor interactor) {
        return interactor;
    }

    //  READ //

    @Provides
    @PerActivity
    ReadContract.ReadPresenter<ReadContract.ReadView, ReadContract.ReadInteractor> provideReadPresenter(
            ReadPresenter<ReadContract.ReadView, ReadContract.ReadInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ReadContract.ReadInteractor provideReadInteractor(ReadInteractor interactor) {
        return interactor;
    }

    //  HOME //

    @Provides
    @PerActivity
    HomeContract.HomePresenter<HomeContract.HomeView, HomeContract.HomeInteractor> provideHomePresenter(
            HomePresenter<HomeContract.HomeView, HomeContract.HomeInteractor> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    HomeContract.HomeInteractor provideHomeInteractor(HomeInteractor interactor) {
        return interactor;
    }

    // ADAPTERS

    @Provides
    StoryAdapter provideStoryAdapter() {
        return new StoryAdapter(new ArrayList<Story>());
    }

    @Provides
    CommentAdapter provideCommentAdapter() {
        return new CommentAdapter(new ArrayList<Comment>());
    }


    // Linear Layout Manager

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
