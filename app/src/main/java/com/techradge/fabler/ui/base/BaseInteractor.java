package com.techradge.fabler.ui.base;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.utils.AppConstants;

import javax.inject.Inject;

public class BaseInteractor implements MvpInteractor {

    private final PreferencesHelper mPreferencesHelper;
    //private final ApiHelper mApiHelper;

    @Inject
    public BaseInteractor(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
        //mApiHelper = apiHelper;
    }

    @Override
    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    @Override
    public void setAccessToken(String accessToken) {
        getPreferencesHelper().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            boolean isUserLoggedIn,
            String userFullName,
            String userEmail,
            String userPhotoUrl,
            AppConstants.LoggedInMode loggedInMode,
            String accessToken) {

        getPreferencesHelper().setUserLoggedIn(isUserLoggedIn);
        getPreferencesHelper().setUserFullName(userFullName);
        getPreferencesHelper().setUserEmail(userEmail);
        getPreferencesHelper().setUserPhotoUrl(userPhotoUrl);
        getPreferencesHelper().setCurrentUserLoggedInMode(loggedInMode);
        getPreferencesHelper().setAccessToken(accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(false,
                null,
                null,
                null,
                AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null);
    }
}
