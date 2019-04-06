package com.techradge.fabler.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.techradge.fabler.data.firebase.Database;
import com.techradge.fabler.data.firebase.operations.user.UserDataOp;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.data.prefs.AppPrefsManager;

import java.lang.ref.WeakReference;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private static final String TAG = "LoginPresenter";
    // Weak Reference for View
    private final WeakReference<LoginContract.LoginView> mView;
    private UserDataOp userDataOp;
    private AppPrefsManager appPrefsManager;

    LoginPresenter(@NonNull LoginContract.LoginView loginView) {
        mView = new WeakReference<>(checkNotNull(loginView, "LoginView cannot be null."));
        userDataOp = new UserDataOp(Database.getFirebaseDatabase(), mView.get().getContext());
        appPrefsManager = new AppPrefsManager(mView.get().getContext());

        if (isUserLoggedIn()) {
            // Launch Home Activity
            mView.get().starHomeActivity();
        } else
            mView.get().startFirebaseUIAuth();
    }

    // User data insertion
    @Override
    public void insertUserData(User user) {
        try {
            userDataOp.insertUserData(user, mView.get().getContext());
            appPrefsManager.setUserLoggedIn(true);
            appPrefsManager.setUserFullName(user.getFullName());
            appPrefsManager.setUserEmail(user.getEmail());
            appPrefsManager.setUserPhotoUrl(user.getPhotoURL());

            // Launch Welcome Activity
            mView.get().startWelcomeActivity(user);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    @Override
    public boolean isUserLoggedIn() {
        return appPrefsManager.isUserLoggedIn();
    }

    @Override
    public void start() {
    }
}