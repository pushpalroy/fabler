package com.techradge.fabler.ui.compose;

import android.content.Context;

import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.ui.base.BaseView;

public interface ComposeContract {
    interface ComposeView extends BaseView<ComposePresenter> {

        void showDraftSavedMessage();
    }

    interface ComposePresenter extends BasePresenter {
        void onSaveOptionSelected(Story story);

        void onPublishOptionSelected(Story story, Context context);

        void onStorySavedLocally();
    }

    interface ComposeInteractor {
        void insertStoryInLocalDb(Story story);
    }
}
