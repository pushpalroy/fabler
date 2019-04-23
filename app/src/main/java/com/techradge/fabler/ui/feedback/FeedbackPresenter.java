package com.techradge.fabler.ui.feedback;

import com.techradge.fabler.data.model.Feedback;
import com.techradge.fabler.ui.base.BasePresenter;
import com.techradge.fabler.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class FeedbackPresenter<V extends FeedbackContract.FeedbackView, I extends FeedbackContract.FeedbackInteractor>
        extends BasePresenter<V, I>
        implements FeedbackContract.FeedbackPresenter<V, I> {

    @Inject
    FeedbackPresenter(I mvpInteractor, SchedulerProvider schedulerProvider,
                      CompositeDisposable compositeDisposable) {
        super(mvpInteractor, schedulerProvider, compositeDisposable);
        getInteractor().setPresenter(this);
    }

    @Override
    public void onSendButtonClicked(String storyId, String feedbackText) {
        getInteractor().insertFeedbackFirebase(storyId, feedbackText);
        getMvpView().resetFeedbackEditor();
        getMvpView().hideKeyboard();
    }

    @Override
    public void onNewFeedbackPrepare() {
        getMvpView().showCustomLoader();
    }

    @Override
    public void onNewFeedbackListFetched(List<Feedback> feedbackList) {
        getMvpView().showAllFeedbacks(feedbackList);
        getMvpView().hideCustomLoader();
    }

    @Override
    public void setUpRemoteDatabase(String storyId) {
        getInteractor().setUpFirebaseDatabase(storyId);
    }
}
