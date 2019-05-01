package com.techradge.fabler.ui.comment;

import com.techradge.fabler.data.model.Comment;
import com.techradge.fabler.ui.base.MvpInteractor;
import com.techradge.fabler.ui.base.MvpPresenter;
import com.techradge.fabler.ui.base.MvpView;

import java.util.List;

public interface CommentContract {
    interface CommentView extends MvpView {

        void setUpRecyclerView();

        void showAllComments(List<Comment> commentList);

        void resetCommentEditor();

        void showCustomLoader();

        void hideCustomLoader();
    }

    interface CommentPresenter<V extends MvpView, I extends MvpInteractor>
            extends MvpPresenter<V, I> {

        void onSendButtonClicked(String storyId, String commentText);

        void setUpRemoteDatabase(String storyId);

        void onNewCommentPrepare();

        void onNewCommentListFetched(List<Comment> commentList);
    }

    interface CommentInteractor extends MvpInteractor {

        void insertCommentFirebase(String storyId, String commentBody);

        void updateStoryFirebase(String storyId, String comments);

        void setUpFirebaseDatabase(String storyId);

        void setPresenter(CommentPresenter commentPresenter);
    }
}
