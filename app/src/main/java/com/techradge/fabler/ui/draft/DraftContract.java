package com.techradge.fabler.ui.draft;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v4.app.FragmentActivity;

import com.techradge.fabler.data.local.viewmodel.MainViewModel;
import com.techradge.fabler.data.model.Story;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

import java.util.List;

public interface DraftContract {

    interface DraftView extends MvpView {
        void showAllDrafts(List<Story> drafts);

        void openComposeActivity(Story story);
    }

    interface DraftPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {
        void onViewPrepared(MainViewModel mainViewModel, FragmentActivity fragmentActivity);

        void removeListeners(LifecycleOwner lifecycleOwner);
    }

    interface DraftInteractor extends MvpInteractor {
        void setPresenter(DraftContract.DraftPresenter presenter);
    }
}
