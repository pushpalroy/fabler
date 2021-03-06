package com.techradge.fabler.data.prefs;

import com.techradge.fabler.utils.AppConstants;

import javax.inject.Singleton;

@Singleton
public interface PreferencesHelper {
    void setFirstTimeLaunch(boolean isFirstTime);

    boolean isFirstTimeLaunch();

    void setUserLoggedIn(boolean isUserLoggedIn);

    boolean isUserLoggedIn();

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(AppConstants.LoggedInMode mode);

    void setUserFullName(String userFullName);

    String getUserFullName();

    void setUserEmail(String userEmail);

    String getUserEmail();

    void setWidgetStoryTitle(String widgetStoryTitle);

    String getWidgetStoryTitle();

    void setWidgetStoryBody(String widgetStoryBody);

    String getWidgetStoryBody();

    void setWidgetStoryAuthor(String widgetStoryAuthor);

    String getWidgetStoryAuthor();

    void setUserPhotoUrl(String userPhotoUrl);

    String getUserPhotoUrl();

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getFirebaseUid();

    void setFirebaseUid(String uid);
}
