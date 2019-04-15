package com.techradge.fabler.ui.main;

import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface MainContract {

    interface MainView extends MvpView {

        void populateUserDetails(String userFullName, String userEmail, String userPhotoUrl);

        void closeNavDrawer();

        void returnToHome();

        void onHomeSelected();

        void onComposeSelected();

        void onDraftSelected();

        void openLoginActivity();

        void onLogoutSelected();
    }

    interface MainPresenter<V extends MainView, I extends MainInteractor>
            extends MvpPresenter<V, I> {
        void loadUserData();

        void onUserDataLoaded(String userFullName, String userEmail, String userPhotoUrl);

        void setUserLoggedOut();
    }

    interface MainInteractor extends MvpInteractor {
        void fetchUserDetails();

        void setPresenter(MainContract.MainPresenter presenter);
    }
}
