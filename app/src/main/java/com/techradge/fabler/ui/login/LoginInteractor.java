package com.techradge.fabler.ui.login;

import android.content.Context;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.User;
import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.data.remote.RemoteFireDatabase;
import com.techradge.fabler.data.remote.operations.user.UserFireOp;
import com.techradge.fabler.di.ActivityContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

import timber.log.Timber;

public class LoginInteractor extends BaseInteractor implements LoginContract.LoginInteractor {

    private PreferencesHelper mPreferencesHelper;
    private UserFireOp userFireOp;
    private Context mContext;

    @Inject
    public LoginInteractor(@ActivityContext Context context,
                           PreferencesHelper preferencesHelper) {
        super(preferencesHelper);

        userFireOp = new UserFireOp(RemoteFireDatabase.getFirebaseDatabase(), context);
        mPreferencesHelper = preferencesHelper;
        mContext = context;
    }

    @Override
    public boolean isUserLoggedIn() {
        return mPreferencesHelper.isUserLoggedIn();
    }

    // User data insertion
    @Override
    public void insertUserDataLocal(User user, MainViewModel mainViewModel) {
        try {
            mainViewModel.insertUser(user);
            mPreferencesHelper.setUserLoggedIn(true);
            mPreferencesHelper.setUserFullName(user.getFullName());
            mPreferencesHelper.setUserEmail(user.getEmail());
            mPreferencesHelper.setUserPhotoUrl(user.getPhotoURL());
        } catch (Exception e) {
            Timber.e("Exception: %s", e.toString());
        }
    }

    @Override
    public void insertUserDataRemote(User user) {
        try {
            userFireOp.insertUserData(user, mContext);
        } catch (Exception e) {
            Timber.e("Exception: %s", e.toString());
        }
    }
}
