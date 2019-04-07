package com.techradge.fabler.ui.login;

import com.techradge.fabler.data.model.User;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.ui.base.BaseView;

public interface LoginContract {

    interface LoginView extends BaseView<LoginPresenter> {
        void startFirebaseUIAuth();

        void onAuthenticated(User user);

        void starHomeActivity();

        void startWelcomeActivity(User user);
    }

    interface LoginPresenter extends BasePresenter {
        void insertUserData(User user);

        boolean isUserLoggedIn();
    }
}
