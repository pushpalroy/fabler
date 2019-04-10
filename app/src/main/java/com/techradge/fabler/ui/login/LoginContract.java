package com.techradge.fabler.ui.login;

import com.techradge.fabler.data.model.User;
import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface LoginContract {

    interface LoginView extends MvpView {
        void startFirebaseUIAuth();

        void openHomeActivity();

        void openWelcomeActivity(User user);
    }

    @PerActivity
    interface LoginPresenter<V extends LoginView,
            I extends LoginInteractor> extends MvpPresenter<V, I> {

        void launch();

        void onAuthenticated(User user);

        void onUserDataInsertedLocal(User user);
    }

    interface LoginInteractor extends MvpInteractor {
        boolean isUserLoggedIn();

        void insertUserDataLocal(User user);
    }
}
