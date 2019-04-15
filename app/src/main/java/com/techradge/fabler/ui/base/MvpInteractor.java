package com.techradge.fabler.ui.base;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.utils.AppConstants;

public interface MvpInteractor {

    PreferencesHelper getPreferencesHelper();

    void setUserAsLoggedOut();

    void setAccessToken(String accessToken);

    void updateUserInfo(boolean isUserLoggedIn,
                        String userFullName,
                        String userEmail,
                        String userPhotoUrl,
                        AppConstants.LoggedInMode loggedInMode,
                        String accessToken);
}
