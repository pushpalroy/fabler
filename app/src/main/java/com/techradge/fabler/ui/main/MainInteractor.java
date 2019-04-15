package com.techradge.fabler.ui.main;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

public class MainInteractor extends BaseInteractor implements MainContract.MainInteractor {

    private MainContract.MainPresenter mPresenter;
    private PreferencesHelper mPreferencesHelper;

    @Inject
    public MainInteractor(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public void fetchUserDetails() {
        mPresenter.onUserDataLoaded(
                mPreferencesHelper.getUserFullName(),
                mPreferencesHelper.getUserEmail(),
                mPreferencesHelper.getUserPhotoUrl());
    }

    @Override
    public void setPresenter(MainContract.MainPresenter presenter) {
        mPresenter = presenter;
    }
}
