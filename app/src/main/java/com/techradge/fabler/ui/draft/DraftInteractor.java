package com.techradge.fabler.ui.draft;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

public class DraftInteractor extends BaseInteractor implements DraftContract.DraftInteractor {

    DraftContract.DraftPresenter mPresenter;

    @Inject
    public DraftInteractor(PreferencesHelper preferencesHelper) {
        super(preferencesHelper);
    }

    @Override
    public void setPresenter(DraftContract.DraftPresenter presenter) {
        mPresenter = presenter;
    }
}
