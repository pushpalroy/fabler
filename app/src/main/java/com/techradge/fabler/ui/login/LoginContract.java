package com.techradge.fabler.ui.login;

import com.techradge.fabler.base.BasePresenter;
import com.techradge.fabler.base.BaseView;
import com.techradge.fabler.model.User;

public interface LoginContract {

    interface LoginView extends BaseView<LoginPresenter> {
        void startUIAuth();

        void onAuthenticated(User user);

        void starHomeActivity();

        void startWelcomeActivity(User user);
    }

    interface LoginPresenter extends BasePresenter {
        void insertUserData(User user);

        boolean isUserLoggedIn();
    }
}
