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

//    @Override
//    public ApiHelper getApiHelper() {
//        return mApiHelper;
//    }

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
            String accessToken,
            boolean isUserLoggedIn,
            AppConstants.LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        getPreferencesHelper().setAccessToken(accessToken);
        getPreferencesHelper().setUserLoggedIn(isUserLoggedIn);
        getPreferencesHelper().setCurrentUserLoggedInMode(loggedInMode);
        getPreferencesHelper().setUserFullName(userName);
        getPreferencesHelper().setUserEmail(email);
        getPreferencesHelper().setUserPhotoUrl(profilePicPath);

        //updateApiHeader(userId, accessToken);
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                false,
                AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }
}
