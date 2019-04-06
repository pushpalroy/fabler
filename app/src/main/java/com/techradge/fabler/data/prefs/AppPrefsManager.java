package com.techradge.fabler.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.techradge.fabler.R;

public class AppPrefsManager implements PrefsManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared preferences file name
    private static final String PREF_FILE_NAME = "PREF_FILE_FABLER";
    private static final String IS_FIRST_TIME_LAUNCH = "PREF_KEY_IS_FIRST_TIME_LAUNCH";
    private static final String IS_USER_LOGGED_IN = "PREF_KEY_IS_USER_LOGGED_IN";
    private static final String USER_FULL_NAME = "PREF_KEY_USER_FULL_NAME";
    private static final String USER_EMAIL = "PREF_KEY_USER_EMAIL";
    private static final String USER_PHOTO_URL = "PREF_KEY_USER_PHOTO_URL";
    private static final String WIDGET_STORY_TITLE = "PREF_KEY_WIDGET_STORY_TITLE";
    private static final String WIDGET_STORY_AUTHOR = "PREF_KEY_WIDGET_STORY_AUTHOR";
    private static final String WIDGET_STORY_BODY = "PREF_KEY_WIDGET_STORY_BODY";

    @SuppressLint("CommitPrefEdits")
    public AppPrefsManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    @Override
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    @Override
    public void setUserLoggedIn(boolean isUserLoggedIn) {
        editor.putBoolean(IS_USER_LOGGED_IN, isUserLoggedIn);
        editor.commit();
    }

    @Override
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGGED_IN, false);
    }

    @Override
    public void setUserFullName(String userFullName) {
        editor.putString(USER_FULL_NAME, userFullName);
        editor.commit();
    }

    @Override
    public String getUserFullName() {
        return pref.getString(USER_FULL_NAME, _context.getString(R.string.def_val_user_full_name));
    }

    @Override
    public void setUserEmail(String userEmail) {
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    @Override
    public String getUserEmail() {
        return pref.getString(USER_EMAIL, _context.getString(R.string.def_val_user_email));
    }

    @Override
    public void setWidgetStoryTitle(String widgetStoryTitle) {
        editor.putString(WIDGET_STORY_TITLE, widgetStoryTitle);
        editor.commit();
    }

    @Override
    public String getWidgetStoryTitle() {
        return pref.getString(WIDGET_STORY_TITLE, _context.getString(R.string.def_val_widget_story_title));
    }

    @Override
    public void setWidgetStoryBody(String widgetStoryBody) {
        editor.putString(WIDGET_STORY_BODY, widgetStoryBody);
        editor.commit();
    }

    @Override
    public String getWidgetStoryBody() {
        return pref.getString(WIDGET_STORY_BODY, _context.getString(R.string.def_val_widget_author));
    }

    @Override
    public void setWidgetStoryAuthor(String widgetStoryAuthor) {
        editor.putString(WIDGET_STORY_AUTHOR, widgetStoryAuthor);
        editor.commit();
    }

    @Override
    public String getWidgetStoryAuthor() {
        return pref.getString(WIDGET_STORY_AUTHOR, _context.getString(R.string.def_val_author));
    }

    @Override
    public void setUserPhotoUrl(String userPhotoUrl) {
        editor.putString(USER_PHOTO_URL, userPhotoUrl);
        editor.commit();
    }

    @Override
    public String getUserPhotoUrl() {
        return pref.getString(USER_PHOTO_URL, "");
    }
}