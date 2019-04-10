package com.techradge.fabler.ui.base;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.utils.AppConstants;

public interface MvpInteractor {

    //ApiHelper getApiHelper();

    PreferencesHelper getPreferencesHelper();

    void setUserAsLoggedOut();

    void setAccessToken(String accessToken);

    void updateUserInfo(String accessToken,
                        boolean isUserLoggedIn,
                        AppConstants.LoggedInMode loggedInMode,
                        String userName,
                        String email,
                        String profilePicPath);
}
