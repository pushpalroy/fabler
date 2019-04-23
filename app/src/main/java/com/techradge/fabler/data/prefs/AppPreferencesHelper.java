package com.techradge.fabler.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.techradge.fabler.R;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.techradge.fabler.utils.AppConstants.PREF_NAME;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    private static final String PREF_KEY_IS_FIRST_TIME_LAUNCH = "PREF_KEY_IS_FIRST_TIME_LAUNCH";
    private static final String PREF_KEY_IS_USER_LOGGED_IN = "PREF_KEY_IS_USER_LOGGED_IN";
    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";

    private static final String PREF_KEY_USER_FIREBASE_UID = "PREF_KEY_USER_FIREBASE_UID";
    private static final String PREF_KEY_USER_FULL_NAME = "PREF_KEY_USER_FULL_NAME";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";
    private static final String PREF_KEY_USER_PHOTO_URL = "PREF_KEY_USER_PHOTO_URL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_WIDGET_STORY_TITLE = "PREF_KEY_WIDGET_STORY_TITLE";
    private static final String PREF_KEY_WIDGET_STORY_AUTHOR = "PREF_KEY_WIDGET_STORY_AUTHOR";
    private static final String PREF_KEY_WIDGET_STORY_BODY = "PREF_KEY_WIDGET_STORY_BODY";

    @Inject
    @SuppressLint("CommitPrefEdits")
    public AppPreferencesHelper(@ApplicationContext Context context) {
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(PREF_KEY_IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    @Override
    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(PREF_KEY_IS_FIRST_TIME_LAUNCH, true);
    }

    @Override
    public void setUserLoggedIn(boolean isUserLoggedIn) {
        editor.putBoolean(PREF_KEY_IS_USER_LOGGED_IN, isUserLoggedIn).apply();
    }

    @Override
    public boolean isUserLoggedIn() {
        return pref.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return pref.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                AppConstants.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(AppConstants.LoggedInMode mode) {
        editor.putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public void setUserFullName(String userFullName) {
        editor.putString(PREF_KEY_USER_FULL_NAME, userFullName).apply();
    }

    @Override
    public String getUserFullName() {
        return pref.getString(PREF_KEY_USER_FULL_NAME, _context.getString(R.string.def_val_user_full_name));
    }

    @Override
    public void setUserEmail(String userEmail) {
        editor.putString(PREF_KEY_USER_EMAIL, userEmail).apply();
    }

    @Override
    public String getUserEmail() {
        return pref.getString(PREF_KEY_USER_EMAIL, _context.getString(R.string.def_val_user_email));
    }

    @Override
    public void setWidgetStoryTitle(String widgetStoryTitle) {
        editor.putString(PREF_KEY_WIDGET_STORY_TITLE, widgetStoryTitle).apply();
    }

    @Override
    public String getWidgetStoryTitle() {
        return pref.getString(PREF_KEY_WIDGET_STORY_TITLE, _context.getString(R.string.def_val_widget_story_title));
    }

    @Override
    public void setWidgetStoryBody(String widgetStoryBody) {
        editor.putString(PREF_KEY_WIDGET_STORY_BODY, widgetStoryBody).apply();
    }

    @Override
    public String getWidgetStoryBody() {
        return pref.getString(PREF_KEY_WIDGET_STORY_BODY, _context.getString(R.string.def_val_widget_author));
    }

    @Override
    public void setWidgetStoryAuthor(String widgetStoryAuthor) {
        editor.putString(PREF_KEY_WIDGET_STORY_AUTHOR, widgetStoryAuthor).apply();
    }

    @Override
    public String getWidgetStoryAuthor() {
        return pref.getString(PREF_KEY_WIDGET_STORY_AUTHOR, _context.getString(R.string.def_val_author));
    }

    @Override
    public void setUserPhotoUrl(String userPhotoUrl) {
        editor.putString(PREF_KEY_USER_PHOTO_URL, userPhotoUrl).apply();
    }

    @Override
    public String getUserPhotoUrl() {
        return pref.getString(PREF_KEY_USER_PHOTO_URL, "");
    }

    @Override
    public String getAccessToken() {
        return pref.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        editor.putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getFirebaseUid() {
        return pref.getString(PREF_KEY_USER_FIREBASE_UID, null);
    }

    @Override
    public void setFirebaseUid(String uid) {
        editor.putString(PREF_KEY_USER_FIREBASE_UID, uid).apply();
    }
}