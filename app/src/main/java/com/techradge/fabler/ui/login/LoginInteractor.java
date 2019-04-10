package com.techradge.fabler.ui.login;

import android.content.Context;

import com.techradge.fabler.data.firebase.Database;
import com.techradge.fabler.data.firebase.operations.user.UserDataOp;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginInteractor extends BaseInteractor implements LoginContract.LoginInteractor {

    private PreferencesHelper mPreferencesHelper;
    private UserDataOp userDataOp;
    private Context mContext;

    @Inject
    public LoginInteractor(@ActivityContext Context context,
                           PreferencesHelper preferencesHelper) {
        super(preferencesHelper);

        userDataOp = new UserDataOp(Database.getFirebaseDatabase(), context);
        mPreferencesHelper = preferencesHelper;
        mContext = context;
    }

    @Override
    public boolean isUserLoggedIn() {
        return mPreferencesHelper.isUserLoggedIn();
    }

    // User data insertion
    @Override
    public void insertUserDataLocal(User user) {
        try {
            userDataOp.insertUserData(user, mContext);
            mPreferencesHelper.setUserLoggedIn(true);
            mPreferencesHelper.setUserFullName(user.getFullName());
            mPreferencesHelper.setUserEmail(user.getEmail());
            mPreferencesHelper.setUserPhotoUrl(user.getPhotoURL());

        } catch (Exception e) {
            Timber.e("Exception: %s", e.toString());
        }
    }
}
