package com.techradge.fabler.ui.comment;

import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

public interface CommentContract {
    interface CommentView extends MvpView {

    }

    interface CommentPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {

    }

    interface CommentInteractor extends MvpInteractor {

    }
}
