package com.techradge.fabler.ui.read;

import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface ReadContract {
    interface ReadView extends MvpView {
        void sendUpdateWidgetBroadcast();

        void showWidgetAddedMessage();

        void populateStoryData(String... story);
    }

    interface ReadPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {
        void addStoryToWidget(String title, String author, String body);

        void onWidgetDataReady();

        void onStoryAddedToWidget();
    }

    interface ReadInteractor extends MvpInteractor {
        void addToWidget(String title, String author, String body);

        void setPresenter(ReadContract.ReadPresenter presenter);
    }
}
