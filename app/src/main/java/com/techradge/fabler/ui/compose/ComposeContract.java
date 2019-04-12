package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.di.PerActivity;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface ComposeContract {
    interface ComposeView extends MvpView {

        void showMessagePublished();

        void showMessageDraftSaved();
    }

    @PerActivity
    interface ComposePresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {
        void onSaveOptionSelected(Story story);

        void onPublishOptionSelected(Story story);

        void onPublished();

        void setViewModel(MainViewModel mainViewModel);
    }

    interface ComposeInteractor extends MvpInteractor {

        void setPresenter(ComposeContract.ComposePresenter composePresenter);
    }
}
