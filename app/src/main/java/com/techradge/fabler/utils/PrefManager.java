package com.techradge.fabler.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.techradge.fabler.R;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "fabler_pref";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_USER_LOGGED_IN = "IsUserLoggedIn";
    private static final String USER_FULL_NAME = "userFullName";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_PHOTO_URL = "userPhotoUrl";
    private static final String WIDGET_STORY_TITLE = "widgetStoryTitle";
    private static final String WIDGET_STORY_AUTHOR = "widgetStoryAuthor";
    private static final String WIDGET_STORY_BODY = "widgetStoryBody";

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserLoggedIn(boolean isUserLoggedIn) {
        editor.putBoolean(IS_USER_LOGGED_IN, isUserLoggedIn);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGGED_IN, false);
    }

    public void setUserFullName(String userFullName) {
        editor.putString(USER_FULL_NAME, userFullName);
        editor.commit();
    }

    public String getUserFullName() {
        return pref.getString(USER_FULL_NAME, _context.getString(R.string.def_val_user_full_name));
    }

    public void setUserEmail(String userEmail) {
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(USER_EMAIL, _context.getString(R.string.def_val_user_email));
    }

    public void setWidgetStoryTitle(String widgetStoryTitle) {
        editor.putString(WIDGET_STORY_TITLE, widgetStoryTitle);
        editor.commit();
    }

    public String getWidgetStoryTitle() {
        return pref.getString(WIDGET_STORY_TITLE, _context.getString(R.string.def_val_widget_story_title));
    }

    public void setWidgetStoryBody(String widgetStoryBody) {
        editor.putString(WIDGET_STORY_BODY, widgetStoryBody);
        editor.commit();
    }

    public String getWidgetStoryBody() {
        return pref.getString(WIDGET_STORY_BODY, _context.getString(R.string.def_val_widget_author));
    }

    public void setWidgetStoryAuthor(String widgetStoryAuthor) {
        editor.putString(WIDGET_STORY_AUTHOR, widgetStoryAuthor);
        editor.commit();
    }

    public String getWidgetStoryAuthor() {
        return pref.getString(WIDGET_STORY_AUTHOR, _context.getString(R.string.def_val_author));
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        editor.putString(USER_PHOTO_URL, userPhotoUrl);
        editor.commit();
    }

    public String getUserPhotoUrl() {
        return pref.getString(USER_PHOTO_URL, "");
    }
}