package com.techradge.fabler.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.techradge.fabler.database.firebase.Database;
import com.techradge.fabler.database.operations.user.UserDataOp;
import com.techradge.fabler.model.User;
import com.techradge.fabler.utils.PrefManager;

import java.lang.ref.WeakReference;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private static final String TAG = "LoginPresenter";
    // Weak Reference for View
    private final WeakReference<LoginContract.LoginView> mView;
    private UserDataOp userDataOp;
    private PrefManager prefManager;

    LoginPresenter(@NonNull LoginContract.LoginView loginView) {
        mView = new WeakReference<>(checkNotNull(loginView, "LoginView cannot be null."));
        userDataOp = new UserDataOp(Database.getFirebaseDatabase(), mView.get().getContext());
        prefManager = new PrefManager(mView.get().getContext());

        if (isUserLoggedIn()) {
            // Launch Home Activity
            mView.get().starHomeActivity();
        } else
            mView.get().startUIAuth();
    }

    // User data insertion
    @Override
    public void insertUserData(User user) {
        try {
            userDataOp.insertUserData(user, mView.get().getContext());
            prefManager.setUserLoggedIn(true);
            prefManager.setUserFullName(user.getFullName());
            prefManager.setUserEmail(user.getEmail());
            prefManager.setUserPhotoUrl(user.getPhotoURL());

            // Launch Welcome Activity
            mView.get().startWelcomeActivity(user);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.toString());
        }
    }

    @Override
    public boolean isUserLoggedIn() {
        return prefManager.isUserLoggedIn();
    }

    @Override
    public void start() {
    }
}