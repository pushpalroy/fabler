package com.techradge.fabler.service;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

public class SyncInteractor extends BaseInteractor implements SyncMvpInteractor {

    @Inject
    public SyncInteractor(PreferencesHelper preferencesHelper) {

        super(preferencesHelper);
    }
}
