package com.techradge.fabler.ui.feedback;

import com.techradge.fabler.data.model.Feedback;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

import java.util.List;

public interface FeedbackContract {
    interface FeedbackView extends MvpView {

        void setUpRecyclerView();

        void showAllFeedbacks(List<Feedback> feedbackList);

        void resetFeedbackEditor();

        void showCustomLoader();

        void hideCustomLoader();
    }

    interface FeedbackPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {

        void onSendButtonClicked(String storyId, String feedbackText);

        void setUpRemoteDatabase(String storyId);

        void onNewFeedbackPrepare();

        void onNewFeedbackListFetched(List<Feedback> feedbackList);
    }

    interface FeedbackInteractor extends MvpInteractor {

        void insertFeedbackFirebase(String storyId, String feedbackBody);

        void updateStoryFirebase(String storyId, String feedbacks);

        void setUpFirebaseDatabase(String storyId);

        void setPresenter(FeedbackPresenter feedbackPresenter);
    }
}
