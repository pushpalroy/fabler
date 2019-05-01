package com.techradge.fabler.ui.comment;

import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CommentPresenter<V extends CommentContract.CommentView, I extends CommentContract.CommentInteractor>
        extends BasePresenter<V, I>
        implements CommentContract.CommentPresenter<V, I> {

    @Inject
    CommentPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                     CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void onSendButtonClicked(String storyId, String commentText) {
        getInteractor().insertCommentFirebase(storyId, commentText);
        getMvpView().resetCommentEditor();
        getMvpView().hideKeyboard();
    }

    @Override
    public void onNewCommentPrepare() {
        getMvpView().showCustomLoader();
    }

    @Override
    public void onNewCommentListFetched(List<Comment> commentList) {
        getMvpView().showAllComments(commentList);
        getMvpView().hideCustomLoader();
    }

    @Override
    public void setUpRemoteDatabase(String storyId) {
        getInteractor().setUpFirebaseDatabase(storyId);
    }
}
