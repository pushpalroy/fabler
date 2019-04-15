package com.techradge.fabler.ui.read;

import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface ReadContract {
    interface ReadView extends MvpView {

    }

    interface ReadPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {

    }

    interface ReadInteractor extends MvpInteractor {

    }
}
