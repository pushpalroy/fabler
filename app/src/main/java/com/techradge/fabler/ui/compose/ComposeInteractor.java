package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.prefs.PreferencesHelper;
import com.techradge.fabler.di.ApplicationContext;
import com.techradge.fabler.ui.base.BaseInteractor;

import javax.inject.Inject;

public class ComposeInteractor extends BaseInteractor implements ComposeContract.ComposeInteractor {

    private final String TAG = ComposeInteractor.class.getSimpleName();

    private Context context;
    private ComposeContract.ComposePresenter mPresenter;

    @Inject
    ComposeInteractor(PreferencesHelper preferencesHelper, @ApplicationContext Context context) {
        super(preferencesHelper);
        this.context = context;
    }

    @Override
    public void setPresenter(ComposeContract.ComposePresenter composePresenter) {
        mPresenter = composePresenter;
    }
}
