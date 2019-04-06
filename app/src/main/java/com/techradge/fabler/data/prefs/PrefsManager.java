package com.techradge.fabler.data.prefs;

public interface PrefsManager {
    void setFirstTimeLaunch(boolean isFirstTime);

    boolean isFirstTimeLaunch();

    void setUserLoggedIn(boolean isUserLoggedIn);

    boolean isUserLoggedIn();

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
}
